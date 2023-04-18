package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.data.repository.FakeTagRepository
import com.esmt.projet.victodo.feature_tag.data.repository.TagRepositoryImpl
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetAllTagUseCaseTest(){
    private lateinit var getAllTagUseCase: GetAllTagUseCase
    private lateinit var fakeTagRepository: FakeTagRepository
    private val tags = listOf(
        Tag(
            id =1,
            title = "sport"
        )
    )


    @Before
    fun setup(){
        fakeTagRepository = FakeTagRepository()
        getAllTagUseCase = GetAllTagUseCase(fakeTagRepository)

        runBlocking {
            for (tag in tags){
                fakeTagRepository.insertTag(tag)
            }
        }
    }

    @Test
    fun `Should get all tags`() = runBlocking{
        getAllTagUseCase().collect(){
            assertThat(it).isEqualTo(tags)
        }

    }
}