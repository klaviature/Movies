package com.example.movies.data.database.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["movieId", "genre"],
    tableName = "movie_genre_cross_ref"
)
data class MovieGenreCrossRef(
    val movieId: Int,
    val genre: String,
    val orderNumber: Int
)