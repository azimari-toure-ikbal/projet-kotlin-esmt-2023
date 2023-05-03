package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.util.ALL_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.COMPLETED_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.LATE_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.SCHEDULED_TASKS_LIST
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

class GetUtilTaskListsUseCase(
    val repository: TaskListRepository,
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskListWithTasksAndTagsSubTasks>> {
//        val lists = flow(){
//
//            combine(
//                taskRepository.getTasks(),
//                taskRepository.getScheduledTasks(),
//                taskRepository.getLateTasks(),
//                taskRepository.getCompletedTasks()
//            ){
//                    tasks, scheduledTasks, lateTasks, completedTasks ->
//                listOf(
//                    TaskListWithTasksAndTagsSubTasks(
//                        taskList = ALL_TASKS_LIST,
//                        tasks = tasks
//                    ),
//                    TaskListWithTasksAndTagsSubTasks(
//                        taskList = SCHEDULED_TASKS_LIST,
//                        tasks = scheduledTasks
//                    ),
//                    TaskListWithTasksAndTagsSubTasks(
//                        taskList = LATE_TASKS_LIST,
//                        tasks = lateTasks
//                    ),
//                    TaskListWithTasksAndTagsSubTasks(
//                        taskList = COMPLETED_TASKS_LIST,
//                        tasks = completedTasks
//                    )
//                )
//            }.collectLatest {
//                emit(it)
//            }
//        }
        val lists = channelFlow {
            combine(
                taskRepository.getTasks(),
                taskRepository.getScheduledTasks(),
                taskRepository.getLateTasks(),
                taskRepository.getCompletedTasks()
            ){
                    tasks, scheduledTasks, lateTasks, completedTasks ->
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
            }.collect{
                send(it)
            }
        }
        return lists
    }
}