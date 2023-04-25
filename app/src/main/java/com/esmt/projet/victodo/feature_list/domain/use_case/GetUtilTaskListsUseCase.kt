package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.*

class GetUtilTaskListsUseCase(
    val repository: TaskListRepository,
    val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskListWithTasksAndTagsSubTasks>> {
        val lists = flow(){

            combine(
                taskRepository.getTasks(),
                taskRepository.getScheduledTasks(),
                taskRepository.getLateTasks(),
                taskRepository.getCompletedTasks(),
                taskRepository.getTodayTasks()
            ){
                    tasks, scheduledTasks, lateTasks, completedTasks, todayTasks ->
                listOf(
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = TaskList(
                            id = -1,
                            title = "All",
                            color = 7,
                            isPinned = true,
                            isDefault = true,
                            icon = 0
                        ),
                        tasks = tasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = TaskList(
                            id = -2,
                            title = "Scheduled",
                            color = 1,
                            isPinned = true,
                            isDefault = true,
                            icon = 0
                        ),
                        tasks = scheduledTasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = TaskList(
                            id = -3,
                            title = "Late",
                            color = 0,
                            isPinned = true,
                            isDefault = true,
                            icon = 0
                        ),
                        tasks = lateTasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = TaskList(
                            id = -4,
                            title = "Completed",
                            color = 0,
                            isPinned = true,
                            isDefault = true,
                            icon = 0
                        ),
                        tasks = completedTasks
                    )
                )
            }.collectLatest {
                emit(it)
            }
        }
        return lists
    }
}