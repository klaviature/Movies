package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("docs")
    val movies: List<Movie>,
    @SerializedName("pages")
    val pageCount: Int
)
