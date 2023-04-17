package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.exception.list.InvalidListException
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository

class AddTaskListUseCase(
    private val taskListRepository: TaskListRepository,
) {

    @Throws(InvalidListException::class)
    suspend operator fun invoke(list: TaskList) {

        if (list.title.isBlank()) {
            throw InvalidListException("The title of the list cannot be empty.")
        }

        if (list.title.length < 3) {
            throw InvalidListException("The title of the list must be at least 3 characters long.")
        }

        if (list.color == 0) {
            throw InvalidListException("The list must have a color.")
        }

        if (list.icon == null) {
            throw InvalidListException("The list must have an icon.")
        }

        taskListRepository.insertList(list)
    }
}