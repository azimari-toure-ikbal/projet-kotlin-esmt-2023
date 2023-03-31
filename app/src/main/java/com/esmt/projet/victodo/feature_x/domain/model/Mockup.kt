package com.esmt.projet.victodo.feature_x.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mockup(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val category: String,
    val isFavorite: Boolean
) {}
