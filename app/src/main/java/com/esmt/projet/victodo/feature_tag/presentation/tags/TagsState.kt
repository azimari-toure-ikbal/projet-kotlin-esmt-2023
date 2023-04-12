package com.esmt.projet.victodo.feature_tag.presentation.tags

import com.esmt.projet.victodo.feature_tag.domain.model.Tag

data class TagsState(
    val tags : List<Tag> = emptyList(),
)
