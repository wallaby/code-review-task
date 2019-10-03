package com.elt.application

import android.content.Context
import com.google.gson.Gson

class TasksApiService : TasksApiServicing {
    override fun getTasks(context: Context): Array<Task> {
        val assetManager = context.getAssets()
        val inputStream = assetManager.open("tasks.json")
        val tasks = Gson().fromJson(inputStream.bufferedReader(), Array<Task>::class.java)
        Thread.sleep(2000) // two second delay to simulate a slow network connection
        return tasks
    }
}