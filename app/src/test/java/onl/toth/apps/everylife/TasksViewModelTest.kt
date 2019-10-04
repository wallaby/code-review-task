package onl.toth.apps.everylife

import android.content.Context
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.ui.tasks.TasksViewModel
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TasksViewModelTest {
    @Test
    fun testRefreshTasksWithOnlyOneTaskWillOnlyReturnOneFilteredTask() {
        // Given
        val task = Task(1, "some name", "some description", TaskType.general)
        val tasksService = MockTasksService(arrayOf(task))
        val viewModel = TasksViewModel(tasksService, null)

        // When
        viewModel.reloadTable(androidx.test.core.app.ApplicationProvider.getApplicationContext())

        // Then
        Assert.assertThat(1, Matchers.equalTo(viewModel.getFilteredTasksList()?.size))
        Assert.assertThat(1, Matchers.equalTo(viewModel.getFilteredTasksList()?.get(0)?.id))
        Assert.assertThat(
            "some name",
            Matchers.equalTo(viewModel.getFilteredTasksList()?.get(0)?.name)
        )
        Assert.assertThat(
            "some description",
            Matchers.equalTo(viewModel.getFilteredTasksList()?.get(0)?.description)
        )
    }

    @Test
    fun testFilterTasksByGeneralWillOnlyReturnGeneralTasks() {
        // TODO B: Implement this test
        val t = Task(1, "a", "a", TaskType.general)
        val tas = MockTasksService(arrayOf(t))
        val vm = TasksViewModel(tas, null)
        vm.reloadTable(androidx.test.core.app.ApplicationProvider.getApplicationContext())
        var tl = vm.getFilteredTasksList()
        Assert.assertThat(tl?.size, Matchers.equalTo(1))

    }

    // TODO B: Add any other relevant tests

    class MockTasksService(tasks: Array<Task>) : TasksApiServicing {

        private var tasks: Array<Task>

        init {
            this.tasks = tasks
        }

        override fun getTasks(context: Context): Array<Task> {
            return tasks
        }
    }
}