package com.example.movies.domain.repositories

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Review

interface ReviewsRepository {
    suspend fun getReviews(movieId: Int, page: Int): ApiResult<List<Review>>
}