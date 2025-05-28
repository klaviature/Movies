package com.example.movies.domain.repositories

import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getRecommendedMovies(page: Int): Flow<List<Movie>>

    suspend fun searchMovies(query: String, page: Int): Flow<List<Movie>>

    suspend fun getMovieDetails(id: Int): Movie
}