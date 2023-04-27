package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.util.ALL_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.COMPLETED_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.LATE_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.SCHEDULED_TASKS_LIST
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
                        taskList = ALL_TASKS_LIST,
                        tasks = tasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = SCHEDULED_TASKS_LIST,
                        tasks = scheduledTasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = LATE_TASKS_LIST,
                        tasks = lateTasks
                    ),
                    TaskListWithTasksAndTagsSubTasks(
                        taskList = COMPLETED_TASKS_LIST,
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