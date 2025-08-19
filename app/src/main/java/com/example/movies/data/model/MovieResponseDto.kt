package com.example.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDocsResponseDto(
    @SerialName("docs") val docs: List<MovieDto>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int
)
