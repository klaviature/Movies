package com.example.movies.data.mapper

import com.example.movies.data.api.model.ReviewDto
import com.example.movies.data.api.model.ReviewTypeDto
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.ReviewType

fun ReviewDto.toDomain() = Review(
    id = id ?: 0,
    movieId = movieId ?: 0,
    title = title ?: "",
    text = review ?: "",
    type = type?.toDomain() ?: ReviewType.NEUTRAL,
    date = date ?: "",
    author = author ?: "",
    authorRating = userRating ?: 0,
    likes = reviewLikes ?: 0,
    dislikes = reviewDislikes ?: 0
)

fun ReviewTypeDto.toDomain() = when(this) {
    ReviewTypeDto.NEGATIVE -> ReviewType.NEGATIVE
    ReviewTypeDto.NEUTRAL -> ReviewType.NEUTRAL
    ReviewTypeDto.POSITIVE -> ReviewType.POSITIVE
}