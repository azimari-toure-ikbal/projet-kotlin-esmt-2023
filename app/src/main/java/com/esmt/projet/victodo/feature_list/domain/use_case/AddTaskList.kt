package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository

class AddTaskList(
    private val taskListRepository: TaskListRepository,
) {
    suspend operator fun invoke(list: TaskList) {
        taskListRepository.insertList(list)
    }
}