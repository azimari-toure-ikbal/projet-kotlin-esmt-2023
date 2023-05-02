package com.esmt.projet.victodo.core.data.data_source

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esmt.projet.victodo.core.util.Converters
import com.esmt.projet.victodo.feature_list.data.data_source.TaskListDao
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_tag.data.data_source.TagDao
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.data.data_source.TaskDao
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.TagTaskCrossRef
import com.esmt.projet.victodo.feature_task.domain.model.Task
import java.time.format.DateTimeFormatter

@Database(
    entities = [Tag::class, Task::class, TaskList::class, SubTask::class, TagTaskCrossRef::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskListDao: TaskListDao
    abstract val tagDao: TagDao
    abstract val taskDao: TaskDao

    companion object {
        private const val DATABASE_NAME = "task_database"

        @Volatile
        private var instance: TaskDatabase? = null

        fun getInstance(app: Application): TaskDatabase {
            Log.i("infoDataR", "getinstance")
            Log.i("infoDataR", DateTimeFormatter.ISO_TIME.toString())
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(app).also { instance = it }
            }
        }

        private fun buildDatabase(app: Application): TaskDatabase{
            return Room.databaseBuilder(
                app,
                TaskDatabase::class.java,
                DATABASE_NAME
            ).addCallback(TaskDatabaseCallback(app))
                .build()
        }
    }
}