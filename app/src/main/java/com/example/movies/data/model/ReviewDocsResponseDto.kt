package com.example.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDocsResponseDto(
    @SerialName("docs") val docs: List<ReviewDto>? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("limit") val limit: Int? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("pages") val pages: Int? = null
)
