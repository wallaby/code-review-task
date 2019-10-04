package onl.toth.apps.everylife.network


import kotlinx.coroutines.Deferred
import onl.toth.apps.everylife.network.model.Task
import retrofit2.Response
import retrofit2.http.GET

interface TaskApiService {

    @GET("tasks.json")
    fun getTasksAsync(): Deferred<Response<Array<Task>>>
//        val assetManager = TaskListApp.application.assets
//        val inputStream = assetManager.open("tasks.json")
//        val tasks = Gson().fromJson(inputStream.bufferedReader(), Array<Task>::class.java)
//        Thread.sleep(2000) // two second delay to simulate a slow network connection
//        return tasks

}