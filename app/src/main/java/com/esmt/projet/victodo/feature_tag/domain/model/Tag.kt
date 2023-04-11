package com.esmt.projet.victodo.feature_tag.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class Tag(
    @PrimaryKey val id: Int?,
    val title: String,
)
class InvalidTagException(message: String): Exception(message)