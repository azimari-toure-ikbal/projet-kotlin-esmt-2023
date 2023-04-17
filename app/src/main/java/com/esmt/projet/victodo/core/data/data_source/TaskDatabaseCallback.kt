package com.esmt.projet.victodo.core.data.data_source

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.esmt.projet.victodo.feature_list.data.data_source.TaskListDao
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskDatabaseCallback(
    private val listDao: TaskListDao
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val list = TaskList(
            title = "Par défaut",
            color = 0
        )
        GlobalScope.launch {
            listDao.insertList(list)
        }
    }
}