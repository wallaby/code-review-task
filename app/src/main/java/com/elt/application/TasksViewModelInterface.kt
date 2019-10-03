package com.elt.application

import android.content.Context

interface TasksViewModelInterface {
    fun reloadTable(context: Context)
    fun beginRefreshing()
    fun endRefreshing()
    fun getFilteredTasksList(): Array<Task>?
    fun filterClicked(tag: Any?)
}