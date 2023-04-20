package com.esmt.projet.victodo.core.data.data_source

import android.app.Application
import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.esmt.projet.victodo.feature_list.data.data_source.TaskListDao
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class TaskDatabaseCallback(
    private val application: Application
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
//        CoroutineScope(Dispatchers.IO).launch {
//            val list = TaskList(
//                title = "Par défaut",
//                color = 0
//            )
//            val db = TaskDatabase.getInstance(application)
//            db.taskListDao.insertList(list)
//        }
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            runBlocking(Dispatchers.IO) {
                val list = TaskList(
                    title = "Par défaut",
                    color = 0
                )
                val database = TaskDatabase.getInstance(application)
                database.taskListDao.insertList(list)
            }
        }
    }
}