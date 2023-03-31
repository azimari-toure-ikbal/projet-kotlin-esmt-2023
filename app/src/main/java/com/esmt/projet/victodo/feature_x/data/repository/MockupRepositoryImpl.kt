package com.esmt.projet.victodo.feature_x.data.repository

import com.esmt.projet.victodo.feature_x.data.data_source.MockupDao
import com.esmt.projet.victodo.feature_x.domain.model.Mockup
import com.esmt.projet.victodo.feature_x.domain.repository.MockupRepository
import kotlinx.coroutines.flow.Flow

class MockupRepositoryImpl(
    private val mockupDao: MockupDao
): MockupRepository {

    override fun getAll(): Flow<List<Mockup>> {
        return mockupDao.getAll()
    }

    override suspend fun getById(id: Int): Mockup? {
        return mockupDao.getById(id)
    }

    override suspend fun insert(mockup: Mockup) {
        mockupDao.insert(mockup)
    }

    override suspend fun delete(mockup: Mockup) {
        mockupDao.delete(mockup)
    }


}