package com.example.movies.domain.usecases.watched

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.WatchedRepository
import kotlinx.coroutines.flow.Flow

class GetWatchedMoviesUseCase(
    private val repository: WatchedRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getWatchedMovies()
}