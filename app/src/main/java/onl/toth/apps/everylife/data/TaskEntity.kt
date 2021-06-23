package onl.toth.apps.everylife.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import onl.toth.apps.everylife.network.model.Task
import onl.toth.apps.everylife.network.model.TaskType

@Entity(tableName = "Task")
data class TaskEntity(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "type")
    var type: TaskType
) {
    companion object {
        fun from(task: Task): TaskEntity {
            return TaskEntity(task.id, task.name, task.description, task.type)
        }
        fun toTask(taskEntity: TaskEntity): Task {
            return Task(taskEntity.id, taskEntity.name, taskEntity.description, taskEntity.type)
        }
    }
}