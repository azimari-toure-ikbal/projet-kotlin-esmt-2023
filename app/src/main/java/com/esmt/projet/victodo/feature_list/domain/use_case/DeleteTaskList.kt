package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository

class DeleteTaskList(
    private val repository: TaskListRepository
) {
    suspend operator fun invoke(list: TaskListWithTasksAndTagsSubTasks) {
        repository.deleteList(list)
    }
}