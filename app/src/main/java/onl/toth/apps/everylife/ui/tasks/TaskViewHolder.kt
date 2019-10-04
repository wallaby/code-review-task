package onl.toth.apps.everylife.ui.tasks

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.core.content.ContextCompat
import onl.toth.apps.everylife.R
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType
import onl.toth.apps.everylife.databinding.TaskItemBinding


class TaskViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
    private val binding = TaskItemBinding.bind(taskView)

    fun bind(task: Task) {
        binding.taskName.text = task.name
        binding.taskDescription.text = task.description
        binding.taskType.setImageDrawable(
            getTaskTypeImage(task.type, itemView.context)
        )
    }

    private fun getTaskTypeImage(type: TaskType, context: Context): Drawable? {
        return when (type) {
            TaskType.general -> {
                ContextCompat.getDrawable(context, R.mipmap.general)
            }
            TaskType.hydration -> {
                ContextCompat.getDrawable(context, R.mipmap.hydration)
            }
            TaskType.medication -> {
                ContextCompat.getDrawable(context, R.mipmap.medication)
            }
            TaskType.nutrition -> {
                ContextCompat.getDrawable(context, R.mipmap.nutrition)
            }
        }
    }
}