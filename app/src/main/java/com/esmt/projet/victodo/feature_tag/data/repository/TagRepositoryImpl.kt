package com.esmt.projet.victodo.feature_tag.data.repository

import com.esmt.projet.victodo.feature_tag.data.data_source.TagDao
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {
    override fun getTags(): Flow<List<Tag>> {
        return tagDao.getTags()
    }

    override suspend fun getByTagId(id: Long): Tag? {
        return tagDao.getTagById(id)
    }

    override suspend fun insertTag(tag: Tag): Long {
        return tagDao.insertTag(tag)
    }

    override suspend fun deleteTag(tag: Tag) {
        return tagDao.deleteTag(tag)
    }


}