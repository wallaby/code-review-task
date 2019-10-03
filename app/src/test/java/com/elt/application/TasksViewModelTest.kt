package com.elt.application

import android.content.Context
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TasksViewModelTest {
    @Test
    fun testRefreshTasksWithOnlyOneTaskWillOnlyReturnOneFilteredTask() {
        // Given
        val task = Task(1, "some name", "some description",  TaskType.general)
        val tasksService = MockTasksService(arrayOf(task))
        val viewModel = TasksViewModel(tasksService, null)

        // When
        viewModel.reloadTable(androidx.test.core.app.ApplicationProvider.getApplicationContext())

        // Then
        Assert.assertThat(1, equalTo(viewModel.getFilteredTasksList()?.size))
        Assert.assertThat(1, equalTo(viewModel.getFilteredTasksList()?.get(0)?.id))
        Assert.assertThat("some name", equalTo(viewModel.getFilteredTasksList()?.get(0)?.name))
        Assert.assertThat("some description", equalTo(viewModel.getFilteredTasksList()?.get(0)?.description))
    }

    @Test
    fun testFilterTasksByGeneralWillOnlyReturnGeneralTasks() {
        // TODO B: Implement this test
        val t = Task(1, "a", "a",  TaskType.general)
        val tas = MockTasksService(arrayOf(t))
        val vm = TasksViewModel(tas, null)
        vm.reloadTable(androidx.test.core.app.ApplicationProvider.getApplicationContext())
        var tl = vm.getFilteredTasksList()
        Assert.assertThat(tl?.size, equalTo(1))

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
