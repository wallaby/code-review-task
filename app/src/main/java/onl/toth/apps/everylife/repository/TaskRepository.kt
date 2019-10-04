package onl.toth.apps.everylife.repository

import android.util.Log
import onl.toth.apps.everylife.network.TaskApiService
import onl.toth.apps.everylife.network.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskApiService: TaskApiService) {
    suspend fun fetchTasks(): Array<Task>? {
        Log.d("TaskRepository", "Fetching tasks from server...")
        val deferredResponse = taskApiService.getTasksAsync().await()

        return if (deferredResponse.isSuccessful) {
            deferredResponse.body()
        } else {
            throw Exception()
        }

    }
}