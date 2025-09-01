package com.example.movies.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    @SerialName("docs") val docs: List<MovieDto>? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("limit") val limit: Int? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("pages") val pages: Int? = null
)