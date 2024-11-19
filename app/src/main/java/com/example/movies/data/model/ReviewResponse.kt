package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("docs")
    val reviews: List<Review>,
    @SerializedName("pages")
    val pageCount: Int
)
