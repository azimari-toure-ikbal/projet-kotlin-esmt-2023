package com.esmt.projet.victodo.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esmt.projet.victodo.core.util.Converters
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task

@Database(
    entities = [Tag::class, Task::class, TaskList::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MockupDatabase: RoomDatabase() {

    // à compléter avec les DAOs


    companion object {
        const val DATABASE_NAME = "mockup_database"
    }
}