package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubTask(
    @PrimaryKey(autoGenerate = true) val id: Long?=null,
    val name: String,
    val note: String?=null,
    val isEnded: Boolean=false,
    val taskId: Long=0
)