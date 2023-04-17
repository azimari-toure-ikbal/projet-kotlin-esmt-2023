package com.esmt.projet.victodo.feature_task.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["tkId", "tgId"])
data class TagTaskCrossRef (
    @NonNull
    val tkId: Long,
    @NonNull
    @ColumnInfo(index = true)
    val tgId: Long
)