package com.example.movies.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.api.model.MovieType

@Entity(tableName = "movies")
data class MovieEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: MovieType,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "length")
    val length: Int?,

    @ColumnInfo(name = "age_rating")
    val ageRating: Int,

    @ColumnInfo(name = "logo_url")
    val logoUrl: String?,

    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,

    @ColumnInfo(name = "backdrop_url")
    val backdropUrl: String?,

    @ColumnInfo(name = "total_series_length")
    val totalSeriesLength: Int?,

    @ColumnInfo(name = "average_series_length")
    val averageSeriesLength: Int?,

    @ColumnInfo(name = "series")
    val isSeries: Boolean
)