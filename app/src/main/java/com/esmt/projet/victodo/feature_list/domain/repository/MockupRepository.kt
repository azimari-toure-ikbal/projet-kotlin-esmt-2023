package com.esmt.projet.victodo.feature_task.domain.repository

import com.esmt.projet.victodo.feature_task.domain.model.Mockup
import kotlinx.coroutines.flow.Flow

interface MockupRepository {

    fun getAll(): Flow<List<Mockup>>

    suspend fun getById(id: Int): Mockup?

    suspend fun insert(mockup: Mockup)

    suspend fun delete(mockup: Mockup)
}