package com.example.movies.data.api

import com.example.movies.data.api.model.MovieDocsResponseDto
import com.example.movies.data.api.model.MovieDto
import com.example.movies.data.api.model.ReviewDocsResponseDto
import com.example.movies.data.api.model.SearchMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceCoroutines {

    @GET("movie/535341")
    suspend fun validateApiKey(@Header("X-API-KEY") apiKey: String): Response<MovieDto>

    @GET("movie?limit=40&sortField=votes.kp&sortType=-1&sortField=votes.imdb&sortType=-1")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MovieDocsResponseDto>

    @GET("movie/search?sortField=votes.kp&sortType=-1&limit=40")
    suspend fun getMovies(
        @Query("query") name: String,
        @Query("page") page: Int
    ): Response<SearchMovieResponseDto>

    // TODO Add get movies by filters

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<MovieDto>

    @GET("review")
    suspend fun getReviews(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int
    ): Response<ReviewDocsResponseDto>
}