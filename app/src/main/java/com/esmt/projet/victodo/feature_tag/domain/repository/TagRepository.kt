package com.esmt.projet.victodo.feature_tag.domain.repository

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    fun getTags(): Flow<List<Tag>>

    suspend fun getByTagId(id: Long): Tag?

    suspend fun insertTag(tag: Tag): Long

    suspend fun deleteTag(tag: Tag)
}