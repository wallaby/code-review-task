package onl.toth.apps.everylife.repository

import android.util.Log
import onl.toth.apps.everylife.data.TaskDao
import onl.toth.apps.everylife.data.TaskEntity
import onl.toth.apps.everylife.network.TaskApiService
import onl.toth.apps.everylife.network.model.Task
import javax.inject.Inject

open class TaskRepository @Inject constructor(
        private val taskApiService: TaskApiService,
        private val taskDao: TaskDao
    ) {

    suspend fun fetchTasks(): List<Task>? {
        Log.d("TaskRepository", "Fetching tasks from server...")
        val deferredResponse = taskApiService.getTasksAsync().await()

        return if (deferredResponse.isSuccessful) {
            deferredResponse.body()?.also {
                taskDao.deleteAll()
                saveToDatabase(it)
            }?.toList()
        } else {
            throw Exception()
        }
    }

    private suspend fun saveToDatabase(tasks: Array<Task>) {
        taskDao.insertTasks(*tasks.map { task -> TaskEntity.from(task) }.toTypedArray())
    }

    suspend fun getLocalTaskList(): List<Task> {
        return taskDao.getAll().map { TaskEntity.toTask(it) }
    }
}