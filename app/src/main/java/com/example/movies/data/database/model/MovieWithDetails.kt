package com.example.movies.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithDetails(
    @Embedded
    val entity: MovieEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = MovieGenreCrossRef::class,
        projection = ["genre"]
    )
    val genres: List<String> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = MovieCountryCrossRef::class,
        projection = ["country"]
    )
    val countries: List<String> = emptyList()
)