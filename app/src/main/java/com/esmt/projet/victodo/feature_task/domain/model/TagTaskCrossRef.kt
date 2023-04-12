package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["tkId", "tgId"])
data class TagTaskCrossRef (
    val tkId: Long?,
    @ColumnInfo(index = true)
    val tgId: Long?
)