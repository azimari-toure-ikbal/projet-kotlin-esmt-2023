package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.MockupRepository

class MockupDeleteTagUseCase(
    private val repository: MockupRepository
) {
    suspend operator fun invoke(tag: Tag){
        repository.deleteTag(tag)
    }
}