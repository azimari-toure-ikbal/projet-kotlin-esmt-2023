package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.data.repository.FakeTagRepository
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class DeleteTagUseCaseTest(){
    private lateinit var deleteTagUseCase: DeleteTagUseCase
    private lateinit var fakeTagRepository: FakeTagRepository

    @Before
    fun setup(){
        fakeTagRepository = FakeTagRepository()
        deleteTagUseCase = DeleteTagUseCase(fakeTagRepository)
    }

    @Test
    fun `Should delete correctly a tag`(){
        val tag = Tag(
            id= 1,
            title = "sport"
        )
        runBlocking {
            deleteTagUseCase(tag)
            runBlocking {
                fakeTagRepository.deleteTag(tag)
            }
            val deletedTag = tag.id?.let { fakeTagRepository.getByTagId(it) }
            assertThat(deletedTag).isNull()
        }
    }
}