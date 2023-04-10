package com.esmt.projet.victodo.feature_task.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.MockupRepository
import kotlinx.coroutines.flow.Flow


class MockupGetAllTagUseCase(
    private val repository: MockupRepository
) {
    operator fun invoke(): Flow<List<Tag>> {
        return repository.getTags()
    }
}