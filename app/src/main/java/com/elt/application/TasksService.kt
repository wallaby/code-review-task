package com.elt.application

import android.content.Context

interface TasksServiceInterface {
    fun getTasks(context: Context, tasksApiServicing: TasksApiServicing): Array<Task>
}

class TasksService:TasksServiceInterface {
    override fun getTasks(context: Context, tasksApiServicing: TasksApiServicing): Array<Task> {
//        TODO("A: Implement this with the help of TasksApiService")
        return tasksApiServicing.getTasks(context)
    }
}