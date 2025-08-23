package com.example.movies.domain.usecases

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Review
import com.example.movies.domain.repositories.ReviewsRepository

class GetReviewsUseCase(
    private val repository: ReviewsRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): ApiResult<List<Review>> {
        return repository.getReviews(movieId, page)
    }
}