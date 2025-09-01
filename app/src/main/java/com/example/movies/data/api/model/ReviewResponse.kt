package com.example.movies.data.api.model

import com.example.movies.domain.model.ReviewDeprecated
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("docs")
    val reviews: List<ReviewDeprecated>,
    @SerializedName("pages")
    val pageCount: Int
)
