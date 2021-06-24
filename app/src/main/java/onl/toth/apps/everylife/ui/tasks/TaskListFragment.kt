package onl.toth.apps.everylife.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import onl.toth.apps.everylife.TaskListApp.Companion.application
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.databinding.TaskListFragmentBinding
import onl.toth.apps.everylife.tools.connectivity.ConnectivityLiveData
import javax.inject.Inject

class TaskListFragment  : Fragment() {

    lateinit var viewModel: TasksViewModel

    private lateinit var connectivityLiveData: ConnectivityLiveData

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var binding: TaskListFragmentBinding
    private lateinit var taskListAdapter: TaskListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityLiveData = ConnectivityLiveData(application)
        application.appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(TasksViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskListFragmentBinding.inflate(inflater, container, false)

        val rootView = binding.root

        setupFilters()

        setupTaskListView(binding.taskList)
        return rootView
    }

    private fun setupFilters() {
        binding.filterGeneral.setOnClickListener(this::filterClicked)
        binding.filterMedication.setOnClickListener(this::filterClicked)
        binding.filterHydration.setOnClickListener(this::filterClicked)
        binding.filterNutrition.setOnClickListener(this::filterClicked)
    }

    private fun filterClicked(it: View) {
        val filters = mutableListOf<TaskType>()
        if (binding.filterGeneral.isChecked) filters.add(TaskType.general)
        if (binding.filterMedication.isChecked) filters.add(TaskType.medication)
        if (binding.filterHydration.isChecked) filters.add(TaskType.hydration)
        if (binding.filterNutrition.isChecked) filters.add(TaskType.nutrition)
        viewModel.updateFilters(filters)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseObservers()
        viewModel.onFragmentReady()
    }

    private fun initialiseObservers() {
        viewModel.filteredTasks.observe(viewLifecycleOwner, {
            taskListAdapter.updateTasks(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, {
            when(it) {
                TaskLoadingState.LOADING -> {
                    showLoadingSpinner()
                    hideList()
                }
                TaskLoadingState.LOADED -> {
                    showList()
                    hideLoadingSpinner()
                }
                TaskLoadingState.ERROR, null -> {
                    Log.d("TaskListFragment", "Couldn't load list of tasks.")
                    hideLoadingSpinner()
                    showList()
                }
            }
        })

        connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                    binding.noNetworkBar.visibility = View.GONE
                    viewModel.onFragmentReady()
                }
                false -> {
                    binding.noNetworkBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun hideLoadingSpinner() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoadingSpinner() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showList() {
        binding.taskList.visibility = View.VISIBLE
    }

    private fun hideList() {
        binding.taskList.visibility = View.INVISIBLE
    }


    private fun setupTaskListView(taskListView: RecyclerView) {
        taskListAdapter = TaskListAdapter()
        taskListView.adapter = taskListAdapter
        taskListView.layoutManager = LinearLayoutManager(context)
        taskListView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}

