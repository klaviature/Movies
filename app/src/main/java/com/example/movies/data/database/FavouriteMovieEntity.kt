package com.example.movies.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.model.Poster
import com.example.movies.data.model.Rating
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_movies")
data class FavouriteMovieEntity(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("alternativeName")
    var alternativeName: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("year")
    var year: Int? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("movieLength")
    var movieLength: Int? = null,
    @SerializedName("ageRating")
    var ageRating: Int? = null,
    @Embedded
    @SerializedName("poster")
    var poster: Poster? = null,
    @Embedded
    @SerializedName("rating")
    var rating: Rating? = null
)
