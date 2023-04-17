package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository

class GetTaskListUseCase (
    private val repository: TaskListRepository
        ){
    suspend operator fun invoke(id: Long) = repository.getListById(id)
}