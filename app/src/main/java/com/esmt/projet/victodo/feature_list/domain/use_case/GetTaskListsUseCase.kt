package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow

class GetTaskListsUseCase(
    private val repository: TaskListRepository
) {
    operator fun invoke(): Flow<List<TaskListWithTasksAndTagsSubTasks>> {
        return repository.getLists()
    }
}
