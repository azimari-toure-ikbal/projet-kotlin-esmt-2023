package com.esmt.projet.victodo.feature_tag.domain.repository

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface MockupRepository {

    fun getTags(): Flow<List<Tag>>

    suspend fun getByTagId(id: Int): Tag?

    suspend fun insertTag(tag: Tag)

    suspend fun deleteTag(tag: Tag)
}