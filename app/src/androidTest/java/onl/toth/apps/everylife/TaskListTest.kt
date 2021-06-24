package onl.toth.apps.everylife

import android.content.Context
import android.net.wifi.WifiManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CompletableDeferred
import onl.toth.apps.everylife.data.TaskDao
import onl.toth.apps.everylife.network.TaskApiService
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.repository.TaskRepository
import onl.toth.apps.everylife.ui.tasks.TaskListFragment
import onl.toth.apps.everylife.ui.tasks.TasksViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class TaskListTest {

    private lateinit var fragmentFactory: TestFragmentFactory

    @Mock
    private lateinit var taskApiService: TaskApiService

    @Mock
    private lateinit var taskDao: TaskDao

    private lateinit var app: TaskListApp

    private lateinit var taskRepository: TaskRepository

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        app = ApplicationProvider.getApplicationContext<TaskListApp>()
        app.appComponent = mock() //disable dagger
        taskRepository = TaskRepository(taskApiService, taskDao)
        tasksViewModel = TasksViewModel(taskRepository)
        fragmentFactory = TestFragmentFactory()
        whenever(fragmentFactory.viewModelFactory.create(TasksViewModel::class.java))
            .thenReturn(tasksViewModel)
    }

    @Test
    fun testListLoad() {
        val testName = "testName"
        val testDescription = "testDescription"
        val tasks = arrayOf(Task(1, testName, testDescription, TaskType.general))
        whenever(taskApiService.getTasksAsync())
            .thenReturn(CompletableDeferred(Response.success(tasks)))
        val scenario = launchFragmentInContainer<TaskListFragment>(factory = fragmentFactory)
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withText(testName)).check(matches(isDisplayed()))
    }
}


class TestFragmentFactory : FragmentFactory() {

    var viewModelFactory: ViewModelProvider.Factory = mock()

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            TaskListFragment::class.java -> {
                val fragment = TaskListFragment()
                fragment.viewModelFactory = viewModelFactory
                fragment
            }
            else -> super.instantiate(classLoader, className)
        }
}
