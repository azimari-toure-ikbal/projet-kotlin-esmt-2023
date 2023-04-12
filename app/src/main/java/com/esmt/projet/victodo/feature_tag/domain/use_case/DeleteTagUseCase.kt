package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository

class DeleteTagUseCase(
    private val repository: TagRepository
) {
    suspend operator fun invoke(tag: Tag){
        repository.deleteTag(tag)
    }
}