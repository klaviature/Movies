package com.example.movies.domain.usecases.watched

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.WatchedRepository

class RemoveMovieFromWatchedUseCase(
    private val repository: WatchedRepository
) {
    suspend operator fun invoke(movie: Movie): Result<Unit> = repository.removeFromWatched(movie)
}