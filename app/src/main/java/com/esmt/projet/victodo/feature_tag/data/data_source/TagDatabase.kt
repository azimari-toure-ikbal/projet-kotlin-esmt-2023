package com.esmt.projet.victodo.feature_tag.data.data_source

import androidx.room.Database
import com.esmt.projet.victodo.feature_tag.domain.model.Tag

@Database(
    entities =[Tag::class],
    version = 1
)
abstract class TagDatabase {
    abstract val tagDao: TagDao
    companion object{
        const val DATABASE_NAME="tags_db"
    }
}