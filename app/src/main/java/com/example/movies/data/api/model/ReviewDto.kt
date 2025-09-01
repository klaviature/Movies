package com.example.movies.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("movieId") val movieId: Int? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("type") val type: ReviewTypeDto? = null,
    @SerialName("review") val review: String? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("author") val author: String? = null,
    @SerialName("userRating") val userRating: Int? = null,
    @SerialName("authorId") val authorId: Int? = null,
    @SerialName("reviewLikes") val reviewLikes: Int? = null,
    @SerialName("reviewDislikes") val reviewDislikes: Int? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
    @SerialName("createdAt") val createdAt: String? = null
)

@Serializable
enum class ReviewTypeDto {
    @SerialName("Позитивный")
    POSITIVE,

    @SerialName("Негативный")
    NEGATIVE,

    @SerialName("Нейтральный")
    NEUTRAL
}
