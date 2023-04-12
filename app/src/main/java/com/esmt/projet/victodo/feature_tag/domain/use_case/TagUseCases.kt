package com.esmt.projet.victodo.feature_tag.domain.use_case

data class TagUseCases(
    val getAllTagUseCase: GetAllTagUseCase,
    val getTagUseCase: GetTagUseCase,
    val deleteTagUseCase: DeleteTagUseCase,
    val addTagUseCase: AddTagUseCase
)
