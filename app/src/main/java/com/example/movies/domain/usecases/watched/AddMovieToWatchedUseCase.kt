package com.example.movies.domain.usecases.watched

import com.example.movies.domain.model.MovieDeprecated
import com.example.movies.domain.repositories.WatchedRepository

class AddMovieToWatchedUseCase(
    private val repository: WatchedRepository
) {
    suspend operator fun invoke(movie: MovieDeprecated): Result<Unit> = repository.addToWatched(movie)
}