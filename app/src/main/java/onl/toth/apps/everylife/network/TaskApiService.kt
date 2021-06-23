package onl.toth.apps.everylife.network


import kotlinx.coroutines.Deferred
import onl.toth.apps.everylife.network.model.Task
import retrofit2.Response
import retrofit2.http.GET

interface TaskApiService {

    @GET("tasks.json")
    fun getTasksAsync(): Deferred<Response<Array<Task>>>
}