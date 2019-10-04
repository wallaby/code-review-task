package onl.toth.apps.everylife.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import onl.toth.apps.everylife.TaskListApp.Companion.application
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.databinding.TaskListFragmentBinding
import onl.toth.apps.everylife.tools.connectivity.ConnectivityLiveData
import javax.inject.Inject

class TaskListFragment : Fragment() {

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


        binding.filterGeneral.setOnClickListener {
            viewModel.filterClicked(TaskType.general)
        }

        binding.filterMedication.setOnClickListener {
            viewModel.filterClicked(TaskType.medication)
        }

        binding.filterHydration.setOnClickListener {
            viewModel.filterClicked(TaskType.hydration)
        }

        binding.filterNutrition.setOnClickListener {
            viewModel.filterClicked(TaskType.nutrition)
        }
        setupTaskListView(binding.taskList, emptyArray())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseObservers()
        viewModel.onFragmentReady()
    }

    private fun initialiseObservers() {
        viewModel.filteredTasks.observe(viewLifecycleOwner, {
            taskListAdapter.updateFilter(it)
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
                TaskLoadingState.ERROR, null -> TODO("Handle errors")
            }
        })

        connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                    viewModel.onFragmentReady()
//                    statusButton.visibility = View.GONE
//                    moviesRecyclerView.visibility = View.VISIBLE
//                    searchEditText.visibility = View.VISIBLE
                }
                false -> {
//                    statusButton.visibility = View.VISIBLE
//                    moviesRecyclerView.visibility = View.GONE
//                    searchEditText.visibility = View.GONE
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
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideList() {
        binding.progressBar.visibility = View.INVISIBLE
    }


    private fun setupTaskListView(taskListView: RecyclerView, tasks: Array<Task>) {
        taskListAdapter = TaskListAdapter(tasks)
        taskListView.adapter = taskListAdapter
        taskListView.layoutManager = LinearLayoutManager(context)
    }

}