package com.example.movies.data.repository

import com.example.movies.data.mapper.toDomain
import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.SearchMovie
import com.example.movies.domain.repositories.MoviesRepository
import java.io.IOException

//object MoviesRepositoryImpl : MoviesRepository {
//
//    val meme = ApiFactoryCoroutines.apiService
//
//    override suspend fun getRecommendedMovies(page: Int): ApiResult<List<Movie>> {
//        try {
//            val response = meme.getMovies(page)
//            return if (response.isSuccessful) {
//                ApiResult.Success(
//                    response.body()?.docs?.map { it.toDomain() } ?: emptyList()
//                )
//            } else {
//                when (response.code()) {
//                    401 -> ApiResult.Error.Unauthorized
//                    403 -> ApiResult.Error.Forbidden
//                    404 -> ApiResult.Error.NotFound
//                    else -> ApiResult.Error.Unknown(
//                        code = response.code(),
//                        message = response.message(),
//                        error = response.errorBody()?.string() ?: ""
//                    )
//                }
//            }
//        } catch (e: IOException) {
//            return ApiResult.Error.Unknown(
//                code = 0,
//                message = e.message.toString(),
//                error = e.javaClass.name
//            )
//        }
//    }
//
//    override suspend fun searchMovies(query: String, page: Int): ApiResult<List<SearchMovie>> {
//        val response = meme.getMovies(query, page)
//        return if (response.isSuccessful) {
//            ApiResult.Success(
//                response.body()?.docs?.map { it.toDomain() } ?: emptyList()
//            )
//        } else {
//            when (response.code()) {
//                401 -> ApiResult.Error.Unauthorized
//                403 -> ApiResult.Error.Forbidden
//                404 -> ApiResult.Error.NotFound
//                else -> ApiResult.Error.Unknown(
//                    code = response.code(),
//                    message = response.message(),
//                    error = response.errorBody()?.string() ?: ""
//                )
//            }
//        }
//    }
//
//    override suspend fun getMovieDetails(id: Int): ApiResult<Movie> {
//        val response = meme.getMovie(id)
//        return if (response.isSuccessful) {
//            ApiResult.Success(
//                response.body()!!.toDomain()
//            )
//        } else {
//            when (response.code()) {
//                401 -> ApiResult.Error.Unauthorized
//                403 -> ApiResult.Error.Forbidden
//                404 -> ApiResult.Error.NotFound
//                else -> ApiResult.Error.Unknown(
//                    code = response.code(),
//                    message = response.message(),
//                    error = response.errorBody()?.string() ?: ""
//                )
//            }
//        }
//    }
//}