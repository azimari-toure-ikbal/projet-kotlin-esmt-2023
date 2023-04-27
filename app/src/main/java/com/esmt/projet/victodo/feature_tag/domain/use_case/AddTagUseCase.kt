package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.InvalidTagException
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository

class AddTagUseCase(
    private val repository: TagRepository
){
    suspend operator fun invoke(tag: Tag): Long{
        if(tag.title.isBlank()){
            throw InvalidTagException("Veuillez renseigner le nom du tag svp :)")
        }
        return repository.insertTag(tag)
    }
}