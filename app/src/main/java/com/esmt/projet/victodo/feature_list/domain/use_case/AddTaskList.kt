package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class AddTaskList(
    private val taskListRepository: TaskListRepository,
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(list: TaskListWithTasksAndTagsSubTasks) {
        list.tasks.forEach { task ->
            taskRepository.insert(task)
        }
        taskListRepository.insertList(list.taskList)
    }
}