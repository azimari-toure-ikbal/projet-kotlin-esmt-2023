package com.esmt.projet.victodo.feature_task.use_case

import com.esmt.projet.victodo.feature_task.domain.model.Mockup
import com.esmt.projet.victodo.feature_task.domain.repository.MockupRepository
import kotlinx.coroutines.flow.Flow

class MockupGetAllUseCase(
    private val mockupRepository: MockupRepository
) {

    operator fun invoke(): Flow<List<Mockup>> {
        return mockupRepository.getAll()
    }
}