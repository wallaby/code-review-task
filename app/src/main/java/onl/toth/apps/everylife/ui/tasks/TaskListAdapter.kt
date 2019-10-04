package onl.toth.apps.everylife.ui.tasks

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import onl.toth.apps.everylife.R
import onl.toth.apps.everylife.network.model.Task

class TaskListAdapter(private var tasks: Array<Task>) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item, viewGroup, false)

        return TaskViewHolder(v)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    fun updateFilter(filteredTasks: Array<Task>) {
        tasks = filteredTasks
    }
}