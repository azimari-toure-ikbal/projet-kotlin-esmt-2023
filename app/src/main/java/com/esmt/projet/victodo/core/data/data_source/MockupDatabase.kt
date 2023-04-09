package com.esmt.projet.victodo.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esmt.projet.victodo.feature_list.data.data_source.MockupDao
import com.esmt.projet.victodo.feature_list.domain.model.Mockup
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task

@Database(
    entities = [Tag::class, Task::class, List::class],
    version = 1,
    exportSchema = false
)
abstract class MockupDatabase: RoomDatabase() {

    // à compléter avec les DAOs
    abstract val mockupDao: MockupDao

    companion object {
        const val DATABASE_NAME = "mockup_database"
    }
}