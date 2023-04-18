package com.esmt.projet.victodo.feature_tag.domain.use_case

import com.esmt.projet.victodo.feature_tag.data.repository.FakeTagRepository
import com.esmt.projet.victodo.feature_tag.domain.model.InvalidTagException
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddTagUseCaseTest(){

    private lateinit var addTagUseCase: AddTagUseCase
    private lateinit var fakeTagRepository: FakeTagRepository



    @Before
    fun setup(){
        fakeTagRepository = FakeTagRepository()
        addTagUseCase = AddTagUseCase(fakeTagRepository)
    }

    @Test
    fun `Should add a correct tag`(){
        val tags = Tag(
            id= 1,
            title = "sport"
        )
        runBlocking {
            addTagUseCase(tags)
            fakeTagRepository.getTags().collect { tag ->
                Truth.assertThat(tag).contains(tags)
            }
        }
    }

    @Test
    fun `Should throw exception when adding Tag without name`(){
        val tags = Tag(
            id=2,
            title = ""
        )
        val e= assertThrows(
            InvalidTagException::class.java
        ) {
            runBlocking {
                addTagUseCase(tags)
            }
        }
        Truth.assertThat(e).hasMessageThat().startsWith("Veuillez renseigner le nom du tag svp :)")
    }
}