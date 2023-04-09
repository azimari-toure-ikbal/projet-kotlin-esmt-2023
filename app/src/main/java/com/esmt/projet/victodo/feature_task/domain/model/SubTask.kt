package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubTask(
    @PrimaryKey val id: Int,
    val name: String,
    val note: String,
    val isEnded: Boolean,
    val taskId: Int,
)