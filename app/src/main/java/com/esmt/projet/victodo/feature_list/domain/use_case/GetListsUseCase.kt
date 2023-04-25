package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow

class GetListsUseCase(
    private val repository: TaskListRepository
) {

    operator fun invoke(): Flow<List<TaskList>> {
        return repository.getLists()
    }
}