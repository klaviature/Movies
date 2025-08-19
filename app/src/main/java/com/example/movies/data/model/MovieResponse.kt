package com.example.movies.data.model

import com.example.movies.domain.model.MovieDeprecated
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("docs")
    val movies: List<MovieDeprecated>,
    @SerializedName("pages")
    val pageCount: Int
)
