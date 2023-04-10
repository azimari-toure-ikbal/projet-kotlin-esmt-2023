package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.InvalidTagException
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.MockupRepository

class AddTagUseCase(
    private val repository: MockupRepository
){
    suspend operator fun invoke(tag: Tag){
        if(tag.title.isBlank()){
            throw InvalidTagException("Veuillez renseigner le nom du tag svp :)")
        }
        repository.insertTag(tag)
    }
}