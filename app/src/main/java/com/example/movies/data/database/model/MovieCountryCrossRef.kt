package com.example.movies.data.database.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["movieId", "country"],
    tableName = "movie_country_cross_ref"
)
data class MovieCountryCrossRef(
    val movieId: Int,
    val country: String,
    val orderNumber: Int
)