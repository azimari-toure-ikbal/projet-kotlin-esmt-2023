package com.esmt.projet.victodo.feature_x.domain.repository

import com.esmt.projet.victodo.feature_x.domain.model.Mockup
import kotlinx.coroutines.flow.Flow

interface MockupRepository {

    fun getAll(): Flow<List<Mockup>>

    suspend fun getById(id: Int): Mockup?

    suspend fun insert(mockup: Mockup)

    suspend fun delete(mockup: Mockup)
}