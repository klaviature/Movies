package com.example.movies.data.repository

import com.example.movies.data.api.ApiFactoryCoroutines
import com.example.movies.data.mapper.toDomain
import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Review
import com.example.movies.domain.repositories.ReviewsRepository
import java.io.IOException

object ReviewsRepositoryImpl : ReviewsRepository {
    private val apiService = ApiFactoryCoroutines.apiService

    override suspend fun getReviews(movieId: Int, page: Int): ApiResult<List<Review>> {
        return try {
            val response = apiService.getReviews(
                movieId = movieId,
                page = page
            )
            if (response.isSuccessful) {
                ApiResult.Success(response.body()?.docs?.map { it.toDomain() } ?: emptyList())
            } else {
                when (response.code()) {
                    401 -> ApiResult.Error.Unauthorized
                    403 -> ApiResult.Error.Forbidden
                    404 -> ApiResult.Error.NotFound
                    else -> ApiResult.Error.Unknown(
                        code = response.code(),
                        message = response.message(),
                        error = response.errorBody()?.string() ?: ""
                    )
                }
            }
        } catch (e: IOException) {
            ApiResult.Error.Unknown(
                code = 0,
                message = e.message.toString(),
                error = e.javaClass.name
            )
        }
    }
}