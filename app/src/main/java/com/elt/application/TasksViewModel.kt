package com.elt.application

import android.content.Context
import android.view.View
import android.widget.ProgressBar

class TasksViewModel(tasksApiService: TasksApiServicing, fragment: TasksListFragment?) : TasksViewModelInterface {
    var tasksApiService: TasksApiServicing? = null
    var tasks: Array<Task>? = null
    var filteredTasks: Array<Task>? = null
    var fragment: TasksListFragment?

    init {
        this.tasksApiService = tasksApiService
        this.fragment = fragment
    }

    override fun reloadTable(context: Context) {
        tasks = tasksApiService?.getTasks(context)
        filteredTasks = tasks
    }

    override fun beginRefreshing() {
//        TODO("A: implement this so that the progressBar is shown only when loading. " +
//                    "The progressBar should also be adjusted to be central at the top of the screen")
        val view1: ProgressBar? = this.fragment?.view?.findViewById<ProgressBar>(R.id.progressBar)
        view1?.visibility = View.VISIBLE
    }

    override fun endRefreshing() {
//        TODO("A: implement this so that the progressBar is shown only when loading. ")
        val view1: ProgressBar? = fragment?.view?.findViewById<ProgressBar>(R.id.progressBar)
        view1?.visibility = View.INVISIBLE
    }

    override fun getFilteredTasksList(): Array<Task>? {
        return filteredTasks
    }

    override fun filterClicked(tag: Any?) {
//        TODO("B: implement this so that the list will show only the tasks that have the selected TaskType")
        if (filteredTasks?.get(0)?.type?.equals(tag as TaskType)!!) {
            filteredTasks = tasks
        } else {
            val list =
                tasks?.filter {
                    val taskType = tag as TaskType
                    it.type.equals(taskType)
                }
            filteredTasks = list?.toTypedArray()
        }
        val adapter: TasksListAdapter = fragment?.recyclerView?.adapter as TasksListAdapter
        adapter.updateFilter(this!!.filteredTasks!!)
        fragment?.recyclerView?.adapter?.notifyDataSetChanged()
    }
}