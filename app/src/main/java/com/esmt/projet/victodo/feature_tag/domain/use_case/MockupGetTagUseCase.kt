package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.MockupRepository

class MockupGetTagUseCase(
    private val repository: MockupRepository
) {
    suspend operator fun invoke(id:Int):Tag?{
        return repository.getByTagId(id)
    }
}