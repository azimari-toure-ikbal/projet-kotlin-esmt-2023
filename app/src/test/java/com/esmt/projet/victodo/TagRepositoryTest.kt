package com.esmt.projet.victodo

import com.esmt.projet.victodo.feature_tag.data.repository.TagRepositoryImpl
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class TagRepositoryTest {
    private val tagRepository = mockk<TagRepository>()
    private lateinit var tagRepositoryImpl: TagRepositoryImpl
    private val listGetTags:List<Tag> =listOf(Tag(12,"faire du sport"))
    private val flowListGetTags: Flow<List<Tag>> = flowOf(listGetTags)
    private val tag1 = Tag(14,"faire ce que je veux")
    private val tag2 = Tag(15,"dormir")


    @Before
    fun setUp() {
        coEvery {
            tagRepository.getTags()
        } returns flowListGetTags

        coEvery {
            tagRepository.getByTagId(any())
        }returns Tag(14,"faire autre chose")

        coEvery {
            tagRepository.deleteTag(tag1)
        }coAnswers { tag1 }

        coEvery {
            tagRepository.insertTag(tag2)
        }coAnswers { tag2 }

        tagRepositoryImpl = TagRepositoryImpl(tagRepository)
    }

    @Test fun `Should correctly get the tag's list`(){
        val result=tagRepositoryImpl.getTags()
        assertThat(result, CoreMatchers.equalTo(listOf(Tag(12,"fAire du sport"))))
    }

}

