package com.example.movies.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched")
data class WatchedEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)