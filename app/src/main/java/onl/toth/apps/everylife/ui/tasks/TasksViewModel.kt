package onl.toth.apps.everylife.ui.tasks


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.repository.TaskRepository
import javax.inject.Inject

class TasksViewModel @Inject constructor(private var taskRepository: TaskRepository) : ViewModel() {

    private var tasks: MutableLiveData<Array<Task>> = MutableLiveData(emptyArray())
    val filteredTasks: MutableLiveData<Array<Task>> = MutableLiveData(emptyArray())

    val loadingState = MutableLiveData<TaskLoadingState>()


    fun onFragmentReady() {
        fetchTasks()
    }

    private fun fetchTasks() {
        loadingState.value = TaskLoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val  tasksFromServer = taskRepository.fetchTasks()
                tasks.postValue(tasksFromServer)
                loadingState.postValue(TaskLoadingState.LOADED)
            } catch (e: Exception) {
                loadingState.postValue(TaskLoadingState.ERROR)
            }
        }
    }

    fun filterClicked(tag: Any?) {
////        TODO("B: implement this so that the list will show only the tasks that have the selected TaskType")
//        if (filteredTasks?.get(0)?.type?.equals(tag as TaskType)!!) {
//            filteredTasks = tasks
//        } else {
//            val list =
//                tasks?.filter {
//                    val taskType = tag as TaskType
//                    it.type.equals(taskType)
//                }
//            filteredTasks = list?.toTypedArray()
//        }
//        val adapter: TasksListAdapter = fragment?.recyclerView?.adapter as TasksListAdapter
//        adapter.updateFilter(this!!.filteredTasks!!)
//        fragment?.recyclerView?.adapter?.notifyDataSetChanged()
    }
}