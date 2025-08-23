package com.example.movies.data.model

import com.example.movies.domain.model.ReviewDeprecated
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("docs")
    val reviews: List<ReviewDeprecated>,
    @SerializedName("pages")
    val pageCount: Int
)
