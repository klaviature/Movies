package com.example.movies.domain.repositories

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.SearchMovie

interface MoviesRepository {
    suspend fun getRecommendedMovies(page: Int): ApiResult<List<Movie>>

    suspend fun searchMovies(query: String, page: Int): ApiResult<List<SearchMovie>>

    suspend fun getMovieDetails(id: Int): ApiResult<Movie>
}