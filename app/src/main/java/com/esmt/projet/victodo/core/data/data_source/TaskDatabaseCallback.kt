package com.esmt.projet.victodo.core.data.data_source

import android.app.Application
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class TaskDatabaseCallback(
    private val application: Application
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.i("infoDataR", "appel de la callback")
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
            runBlocking {
                val list = TaskList(
                    title = "Par défaut"
                )
                val database = TaskDatabase.getInstance(application)
                Log.i("infoDataR", "ajout de la liste")
                database.taskListDao.insertList(list)
            }
        }
    }
}