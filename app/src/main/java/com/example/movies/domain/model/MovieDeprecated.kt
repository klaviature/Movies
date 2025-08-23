package com.example.movies.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Deprecated(
    message = "Is not compatible with app",
    replaceWith = ReplaceWith(expression = "Movie()")
)
data class MovieDeprecated(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String? = null,
    @SerializedName("alternativeName") var alternativeName: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("movieLength") var movieLength: Int? = null,
    @SerializedName("ageRating") var ageRating: Int? = null,
    @SerializedName("poster") var poster: Poster? = null,
    @SerializedName("rating") var rating: Rating? = null,
    @SerializedName("videos") var videos: VideosDeprecated? = null,
    @SerializedName("genres") var genres: List<Genre>? = null,
    @SerializedName("countries") var countries: List<Country>? = null
) : Serializable
