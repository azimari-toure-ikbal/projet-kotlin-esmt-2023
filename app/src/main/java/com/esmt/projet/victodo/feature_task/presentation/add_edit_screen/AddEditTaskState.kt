package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

data class AddEditTaskState(
    val tasklists: List<TaskList> = emptyList(),
    val listId: Long? = null,
    val tags: List<Tag> = emptyList(),
    val selectedTags: MutableList<Tag> = mutableListOf(),
    val dueDate: LocalDate? = LocalDate.now(),
    val dueTime: LocalTime? = LocalTime.now(),
    val repeatFrequency: String = Task.Companion.RepeatFrequency.NEVER.value,
    val showDeadlineOptions: Boolean = false,
    val priority: String = Task.Companion.Priority.LOW.value
)