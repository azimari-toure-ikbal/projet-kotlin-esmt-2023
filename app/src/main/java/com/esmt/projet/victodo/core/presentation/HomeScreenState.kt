package com.esmt.projet.victodo.core.presentation

import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_tag.domain.model.Tag

data class HomeScreenState(
    val isTagRevealed: Boolean = false,
    val listOfPinnedList: List<TaskListWithTasksAndTagsSubTasks> = emptyList(),
    val listOfTaskList: List<TaskListWithTasksAndTagsSubTasks> = emptyList(),
    val listOfTags: List<Tag> = emptyList(),
)