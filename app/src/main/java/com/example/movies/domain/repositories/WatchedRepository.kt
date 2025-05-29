package com.example.movies.domain.repositories

import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface WatchedRepository {
    fun getWatchedMovies(): Flow<List<Movie>>

    suspend fun addToWatched(movie: Movie): Result<Unit>

    suspend fun removeFromWatched(movie: Movie): Result<Unit>

    suspend fun isWatched(id: Int): Boolean
}