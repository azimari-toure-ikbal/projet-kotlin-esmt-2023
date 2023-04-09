package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["tkId", "tgId"])
data class TagTaskCrossRef (
    val tkId: Int,
    val tgId: Int
)