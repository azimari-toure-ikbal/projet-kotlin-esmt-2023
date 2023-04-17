package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first

class GetUtilTaskListsUseCase(
    val repository: TaskListRepository,
    val taskRepository: TaskRepository
) {
    suspend operator fun invoke() {
        val lists = listOf<TaskListWithTasksAndTagsSubTasks>()

        lists.plus(
            TaskListWithTasksAndTagsSubTasks(
                taskList = TaskList(
                    id = 1,
                    title = "All",
                    color = 7,
                    isPinned = true,
                    isDefault = true,
                    icon = 0
                ),
                tasks = taskRepository.getTasks().first()
            )
        )

        lists.plus(
            TaskListWithTasksAndTagsSubTasks(
                taskList = TaskList(
                    id = 2,
                    title = "Scheduled",
                    color = 1,
                    isPinned = true,
                    isDefault = true,
                    icon = 0
                ),
                tasks = taskRepository.getScheduledTasks().first()
            )
        )

        lists.plus(
            TaskListWithTasksAndTagsSubTasks(
                taskList = TaskList(
                    id = 3,
                    title = "Late",
                    color = 0,
                    isPinned = true,
                    isDefault = true,
                    icon = 0
                ),
                tasks = taskRepository.getLateTasks().first()
            )
        )

        lists.plus(
            TaskListWithTasksAndTagsSubTasks(
                taskList = TaskList(
                    id = 4,
                    title = "Completed",
                    color = 2,
                    isPinned = true,
                    isDefault = true,
                    icon = 0
                ),
                tasks = taskRepository.getCompletedTasks().first()
            )
        )
    }
}