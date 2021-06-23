package onl.toth.apps.everylife.data

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAll(): List<TaskEntity>

    @Query("DELETE FROM task")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(vararg tasks: TaskEntity)

}