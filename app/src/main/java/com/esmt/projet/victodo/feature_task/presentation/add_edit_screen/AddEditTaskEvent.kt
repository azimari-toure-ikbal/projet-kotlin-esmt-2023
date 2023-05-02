package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import androidx.compose.ui.focus.FocusState
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import java.time.LocalDate
import java.time.LocalTime

sealed class AddEditTaskEvent {
    data class EnteredName(val value: String): AddEditTaskEvent()
    data class EnteredNote (val value: String): AddEditTaskEvent()
    data class EnteredPriority(val value: String): AddEditTaskEvent()
    data class EnteredDueDate(val value: LocalDate): AddEditTaskEvent()
    data class EnteredDueTime(val value: LocalTime): AddEditTaskEvent()
    data class EnteredRedundancy(val value: String): AddEditTaskEvent()
    data class EnteredListId(val value: Long): AddEditTaskEvent()
    data class EnteredTag(val value: Tag): AddEditTaskEvent()
    data class RemovedTag(val value: Tag): AddEditTaskEvent()
    data class TagTextFieldFocused(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredTagText(val value: String): AddEditTaskEvent()
    object CreateTag: AddEditTaskEvent()
    object ToggleDeadlineOptions: AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()
}