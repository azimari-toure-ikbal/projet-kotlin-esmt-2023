package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository

class SearchTaskListsUseCase(
    private val repository: TaskListRepository
) {
    operator fun invoke(searchQuery: String) = repository.searchLists(searchQuery)

}
