package onl.toth.apps.everylife.data

import androidx.room.TypeConverter
import onl.toth.apps.everylife.network.model.TaskType

class Converters {
    @TypeConverter
    fun taskTypeFromString(value: String?): TaskType? {
        return value?.let { TaskType.valueOf(it) }
    }

    @TypeConverter
    fun taskTypeToString(taskType: TaskType?): String? {
        return taskType?.name
    }
}
