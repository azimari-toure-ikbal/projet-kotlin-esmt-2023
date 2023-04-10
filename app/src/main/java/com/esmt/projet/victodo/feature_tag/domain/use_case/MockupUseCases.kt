package com.esmt.projet.victodo.feature_task.use_case

import com.esmt.projet.victodo.feature_tag.domain.use_case.MockupAddTagUseCase
import com.esmt.projet.victodo.feature_tag.domain.use_case.MockupDeleteTagUseCase
import com.esmt.projet.victodo.feature_tag.domain.use_case.MockupGetTagUseCase

data class MockupUseCases(
    val getAllTagUseCase: MockupGetAllTagUseCase,
    val getTagUseCase: MockupGetTagUseCase,
    val deleteTagUseCase: MockupDeleteTagUseCase,
    val addTagUseCase: MockupAddTagUseCase
)
