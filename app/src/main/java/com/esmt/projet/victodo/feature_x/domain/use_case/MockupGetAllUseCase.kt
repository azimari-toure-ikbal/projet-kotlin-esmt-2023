package com.esmt.projet.victodo.feature_x.domain.use_case

import com.esmt.projet.victodo.feature_x.domain.model.Mockup
import com.esmt.projet.victodo.feature_x.domain.repository.MockupRepository
import kotlinx.coroutines.flow.Flow

class MockupGetAllUseCase(
    private val mockupRepository: MockupRepository
) {

    operator fun invoke(): Flow<List<Mockup>> {
        return mockupRepository.getAll()
    }
}