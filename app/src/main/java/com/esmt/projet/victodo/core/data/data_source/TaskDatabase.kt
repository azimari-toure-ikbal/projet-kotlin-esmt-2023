package com.esmt.projet.victodo.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esmt.projet.victodo.core.util.Converters
import com.esmt.projet.victodo.feature_list.data.data_source.TaskListDao
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_tag.data.data_source.TagDao
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.TagTaskCrossRef
import com.esmt.projet.victodo.feature_task.domain.model.Task

@Database(
    entities = [Tag::class, Task::class, TaskList::class, SubTask::class, TagTaskCrossRef::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskListDao: TaskListDao
    abstract val tagDao: TagDao

    companion object {
        const val DATABASE_NAME = "mockup_database"
    }
}