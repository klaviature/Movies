package com.example.movies.data.api.model

import kotlinx.serialization.SerialName

data class SearchMovieResponseDto(
    @SerialName("docs") val docs: List<SearchMovieDto>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int
)
