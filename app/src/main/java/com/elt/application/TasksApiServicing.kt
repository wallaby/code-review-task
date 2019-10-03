package com.elt.application

import android.content.Context

interface TasksApiServicing {
    fun getTasks(context: Context): Array<Task>
}