package com.esmt.projet.victodo.feature_tag.data.data_source

import androidx.room.*
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM tag")
    fun getTags(): Flow<List<Tag>>

    @Query("SELECT * FROM tag WHERE id= :id")
    suspend fun getTagById(id: Long): Tag?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Delete
    suspend fun deleteTag(tag: Tag)
}