package onl.toth.apps.everylife.ui.tasks

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import onl.toth.apps.everylife.R
import onl.toth.apps.everylife.network.model.Task

class TaskListAdapter :
    RecyclerView.Adapter<TaskViewHolder>() {

    private val tasks: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item, viewGroup, false)

        return TaskViewHolder(v)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    fun updateTasks(filteredTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(filteredTasks)
        notifyDataSetChanged()
    }
}