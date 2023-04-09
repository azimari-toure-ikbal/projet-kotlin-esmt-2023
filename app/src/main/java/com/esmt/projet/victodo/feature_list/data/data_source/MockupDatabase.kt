package com.esmt.projet.victodo.feature_task.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esmt.projet.victodo.feature_task.domain.model.Mockup

@Database(
    entities = [Mockup::class],
    version = 1,
    exportSchema = false
)
abstract class MockupDatabase: RoomDatabase() {

    abstract val mockupDao: MockupDao

    companion object {
        const val DATABASE_NAME = "mockup_database"
    }
}