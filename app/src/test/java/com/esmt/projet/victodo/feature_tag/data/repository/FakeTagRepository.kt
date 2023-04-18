package com.esmt.projet.victodo.feature_tag.data.repository

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagRepository : TagRepository {

    private val tags = mutableListOf<Tag>()

    override fun getTags(): Flow<List<Tag>> {
        return flow{
            emit(tags)
        }
    }

    override suspend fun getByTagId(id: Long): Tag? {
        return tags.find{it.id == id}
    }

    override suspend fun insertTag(tag: Tag) {
        tags.add(tag)
    }

    override suspend fun deleteTag(tag: Tag) {
        tags.remove(tag)
    }

}