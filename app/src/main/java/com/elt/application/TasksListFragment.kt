package com.elt.application

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TasksListFragment : Fragment() {

    lateinit var viewModel: TasksViewModelInterface

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    lateinit var currentLayoutManagerType: LayoutManagerType
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TasksViewModel(TasksApiService(), this)
        viewModel.beginRefreshing()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tasks_list_fragment, container, false).apply { tag = TAG }

        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            currentLayoutManagerType = savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }
        setRecyclerViewLayoutManager(currentLayoutManagerType)

        context?.let {
            viewModel.reloadTable(it)
            viewModel.getFilteredTasksList()?.let {
                recyclerView.adapter = TasksListAdapter(it)
            }
        }

        val filterGeneral: View = rootView.findViewById(R.id.filter_general)
        filterGeneral.setOnClickListener {
            viewModel.filterClicked(TaskType.general)
        }
        val filterMedication: View = rootView.findViewById(R.id.filter_medication)
        filterMedication.setOnClickListener {
            viewModel.filterClicked(TaskType.medication)
        }
        val filterHydration: View = rootView.findViewById(R.id.filter_hydration)
        filterHydration.setOnClickListener {
            viewModel.filterClicked(TaskType.hydration)
        }
        val filterNutrition: View = rootView.findViewById(R.id.filter_nutrition)
        filterNutrition.setOnClickListener {
            viewModel.filterClicked(TaskType.nutrition)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.endRefreshing()
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = GridLayoutManager(activity, SPAN_COUNT)
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView) {
            layoutManager = this@TasksListFragment.layoutManager
            scrollToPosition(scrollPosition)
        }
    }


    companion object {
        private val TAG = "TasksListFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
    }
}