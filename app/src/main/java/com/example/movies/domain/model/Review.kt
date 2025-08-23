package com.example.movies.domain.model

data class Review(
    val id: Int,
    val movieId: Int,
    val title: String,
    val text: String,
    val type: ReviewType,
    val date: String,
    val author: String,
    val authorRating: Int,
    val likes: Int,
    val dislikes: Int
)
