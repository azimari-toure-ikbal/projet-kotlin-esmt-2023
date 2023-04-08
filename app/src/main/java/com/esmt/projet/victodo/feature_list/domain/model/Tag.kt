package com.esmt.projet.victodo.feature_x.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag (
    @PrimaryKey val id: Int,
    val title: String,
        )