package com.example.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.model.MovieType
import com.example.movies.domain.model.CurrencyValue
import com.example.movies.domain.model.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//@Entity(tableName = "favourite_movies")
//data class MovieEntity(
//    @SerialName("id")
//    @PrimaryKey
//    val id: Int,
//    @SerialName("name")
//    val name: String,
//    @SerialName("type")
//    val type: MovieType,
//    @SerialName("year")
//    val year: Int,
//    @SerialName("description")
//    val description: String,
//    @SerialName("length")
//    val length: Int?,
//    @SerialName("ageRating")
//    val ageRating: Int,
//    @SerialName("logo")
//    val logoUrl: String,
//    @SerialName("poster")
//    val posterUrl: String,
//    @SerialName("backdrop")
//    val backdropUrl: String,
//    @SerialName("genres")
//    val genres: List<String>,
//    @SerialName("countries")
//    val countries: List<String>,
//    @SerialName("totalSeriesLength")
//    val totalSeriesLength: Int?,
//    @SerialName("averageSeriesLength")
//    val averageSeriesLength: Int?,
//    @SerialName("isSeries")
//    val isSeries: Boolean
//)
