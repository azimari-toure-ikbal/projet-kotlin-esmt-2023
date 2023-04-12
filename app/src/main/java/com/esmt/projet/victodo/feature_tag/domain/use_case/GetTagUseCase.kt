package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository

class GetTagUseCase(
    private val repository: TagRepository
) {
    suspend operator fun invoke(id:Long):Tag?{
        return repository.getByTagId(id)
    }
}