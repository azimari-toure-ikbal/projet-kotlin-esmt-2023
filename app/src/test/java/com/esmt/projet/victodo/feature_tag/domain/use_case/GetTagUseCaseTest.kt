package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.data.repository.FakeTagRepository
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetTagUseCaseTest(){
    private lateinit var getTagUseCase: GetTagUseCase
    private lateinit var fakeTagRepository: FakeTagRepository

    @Before
    fun setup(){
        fakeTagRepository = FakeTagRepository()
        getTagUseCase = GetTagUseCase(fakeTagRepository)
    }

    @Test
    fun `Should correctly add one tag by id`(){
        val tag = Tag(
            id= 1,
            title = "finir mes devoirs"
        )

        runBlocking {
            val insertedTag = fakeTagRepository.insertTag(tag)
            val tagFound = Tag(
                id= 1,
                title = "finir mes devoirs"
            )
            assertThat(tagFound).isEqualTo(getTagUseCase(1))
        }


    }
}