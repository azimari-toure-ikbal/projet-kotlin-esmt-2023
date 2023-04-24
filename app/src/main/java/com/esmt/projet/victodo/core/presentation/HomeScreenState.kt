package com.esmt.projet.victodo.core.presentation

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_tag.domain.model.Tag

data class HomeScreenState(
    val searchQuery: String = "",
    val isTagRevealed: Boolean = false,
    val listOfPinnedList: List<TaskListWithTasksAndTagsSubTasks> = emptyList(),
    val listOfTaskList: List<TaskList> = emptyList(),
    val listOfTags: List<Tag> = emptyList(),
)