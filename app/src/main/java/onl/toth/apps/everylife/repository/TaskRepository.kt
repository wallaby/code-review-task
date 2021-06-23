package onl.toth.apps.everylife.repository

import android.util.Log
import onl.toth.apps.everylife.data.TaskDao
import onl.toth.apps.everylife.data.TaskEntity
import onl.toth.apps.everylife.network.TaskApiService
import onl.toth.apps.everylife.network.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
        private val taskApiService: TaskApiService,
        private val taskDao: TaskDao
    ) {

    suspend fun fetchTasks(): Array<Task>? {
        Log.d("TaskRepository", "Fetching tasks from server...")
        val deferredResponse = taskApiService.getTasksAsync().await()

        return if (deferredResponse.isSuccessful) {
            val tasks = deferredResponse.body()
            if (tasks != null) {
                taskDao.insertTasks(*tasks.map { task -> TaskEntity.from(task) }.toTypedArray())
            }
            tasks
        } else {
            throw Exception()
        }
    }


}