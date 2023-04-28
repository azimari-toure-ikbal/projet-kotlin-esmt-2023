package com.esmt.projet.victodo.feature_list.util

import com.esmt.projet.victodo.feature_list.domain.model.TaskList

val ALL_TASKS_LIST = TaskList(
    id = -1,
    title = "All",
    color = 7,
    isPinned = true,
    isDefault = true,
)

val SCHEDULED_TASKS_LIST = TaskList(
    id = -2,
    title = "Scheduled",
    color = 1,
    isPinned = true,
    isDefault = true,
)

val LATE_TASKS_LIST = TaskList(
    id = -3,
    title = "Late",
    color = 0,
    isPinned = true,
    isDefault = true,
)

val COMPLETED_TASKS_LIST = TaskList(
    id = -4,
    title = "Completed",
    color = 0,
    isPinned = true,
    isDefault = true
)