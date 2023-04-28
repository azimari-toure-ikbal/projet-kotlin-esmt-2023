package com.esmt.projet.victodo.feature_list.data.data_source

import androidx.room.*
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {

    @Transaction
    @Query("SELECT * FROM lists")
    fun getTaskLists(): Flow<List<TaskListWithTasksAndTagsSubTasks>>

    @Query("SELECT * FROM lists")
    fun getLists(): Flow<List<TaskList>>

    @Transaction
    @Query("SELECT * FROM lists WHERE id = :id")
    suspend fun getListById(id: Long): TaskListWithTasksAndTagsSubTasks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: TaskList)

    @Transaction
    @Delete
    suspend fun deleteList(list: TaskList)

    //Search list by name
    @Transaction
    @Query("SELECT * FROM lists WHERE title LIKE '%' || :title || '%'")
    fun searchLists(title: String): Flow<List<TaskListWithTasksAndTagsSubTasks>>



}