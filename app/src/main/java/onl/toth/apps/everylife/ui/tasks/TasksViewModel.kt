package onl.toth.apps.everylife.ui.tasks


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.repository.TaskRepository
import javax.inject.Inject

class TasksViewModel @Inject constructor(private var taskRepository: TaskRepository) : ViewModel() {

    private val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())
    private val filters: MutableLiveData<List<TaskType>> = MutableLiveData(TaskType.values().toList())
    val filteredTasks: MediatorLiveData<List<Task>> = MediatorLiveData()

    val loadingState = MutableLiveData<TaskLoadingState>()

    init {
        filteredTasks.addSource(tasks) { updateFilteredTasks() }
        filteredTasks.addSource(filters) { updateFilteredTasks() }
        updateFilteredTasks()
    }

    private fun updateFilteredTasks() {
        filteredTasks.postValue(
            tasks.value?.filter { filters.value?.contains(it.type) ?: false } ?: emptyList())
    }

    fun onFragmentReady() {
        fetchTasks()
    }

    private fun fetchTasks() {
        loadingState.value = TaskLoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val  tasksFromServer = taskRepository.fetchTasks()
                tasks.postValue(tasksFromServer?.toList() ?: emptyList())
                loadingState.postValue(TaskLoadingState.LOADED)
            } catch (e: Exception) {
                val cachedTasks = taskRepository.getLocalTaskList()
                tasks.postValue(cachedTasks)
                loadingState.postValue(TaskLoadingState.ERROR)
            }
        }
    }

    fun updateFilters(filters: List<TaskType>) {
        this.filters.value = filters
    }
}