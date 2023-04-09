package com.esmt.projet.victodo.feature_list.data.data_source

import androidx.room.*
import com.esmt.projet.victodo.feature_list.domain.model.Mockup
import kotlinx.coroutines.flow.Flow

@Dao
interface MockupDao {

    @Query("SELECT * FROM mockup")
    fun getAll(): Flow<List<Mockup>>

    @Query("SELECT * FROM mockup WHERE id = :id")
    suspend fun getById(id: Int): Mockup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mockup: Mockup)

    @Delete
    suspend fun delete(mockup: Mockup)
}