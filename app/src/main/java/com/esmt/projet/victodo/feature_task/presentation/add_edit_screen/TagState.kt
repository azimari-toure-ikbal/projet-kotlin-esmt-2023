package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import com.esmt.projet.victodo.feature_tag.domain.model.Tag

data class TagState(
    val tags: List<Tag> = emptyList(),
    val tagList: List<Tag> = emptyList(),
    val selectedTags: MutableList<Tag> = mutableListOf()
)