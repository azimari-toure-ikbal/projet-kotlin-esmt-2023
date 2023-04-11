package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow


class GetAllTagUseCase(
    private val repository: TagRepository
) {
    operator fun invoke(): Flow<List<Tag>> {
        return repository.getTags()
    }
}