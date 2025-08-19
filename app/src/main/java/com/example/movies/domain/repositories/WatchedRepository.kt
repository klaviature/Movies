package com.example.movies.domain.repositories

import com.example.movies.domain.model.MovieDeprecated
import kotlinx.coroutines.flow.Flow

interface WatchedRepository {
    fun getWatchedMovies(): Flow<List<MovieDeprecated>>

    suspend fun addToWatched(movie: MovieDeprecated): Result<Unit>

    suspend fun removeFromWatched(movie: MovieDeprecated): Result<Unit>

    suspend fun isWatched(id: Int): Boolean
}