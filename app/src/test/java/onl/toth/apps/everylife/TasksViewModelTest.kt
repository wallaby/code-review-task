package onl.toth.apps.everylife

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import onl.toth.apps.everylife.data.TaskDao
import onl.toth.apps.everylife.network.TaskApiService
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.repository.TaskRepository
import onl.toth.apps.everylife.ui.tasks.TasksViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class TasksViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var taskApiService: TaskApiService

    @Mock
    private lateinit var taskDao: TaskDao

    private lateinit var taskRepository: TaskRepository

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        taskRepository = TaskRepository(taskApiService, taskDao)
        tasksViewModel = TasksViewModel(taskRepository)
    }

    @Test
    fun testOnFragmentReadyEmptyList() {
        whenever(taskApiService.getTasksAsync())
            .thenReturn(CompletableDeferred(Response.success(emptyArray())))
        runBlocking { tasksViewModel.onFragmentReady() }
        assertTrue(tasksViewModel.filteredTasks.value?.isEmpty() == true)
    }

    @Test
    fun testOnFragmentReadyFewItemsFiltered() = runBlockingTest {
        val tasks = arrayOf(
            Task(1, "task1name", "task1description", TaskType.general),
            Task(2, "task2name", "task2description", TaskType.nutrition),
            Task(3, "task3name", "task3description", TaskType.general)
        )
        whenever(taskApiService.getTasksAsync())
            .thenReturn(CompletableDeferred(Response.success(tasks)))
        tasksViewModel.onFragmentReady()
        tasksViewModel.updateFilters(listOf(TaskType.general))
        assertEquals(2, tasksViewModel.filteredTasks.value?.size)
    }


}