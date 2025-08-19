package com.example.movies.domain.usecases.favourites

import com.example.movies.domain.model.MovieDeprecated
import com.example.movies.domain.repositories.FavouritesRepository

class AddMovieToFavouritesUseCase(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(movie: MovieDeprecated): Result<Unit> = repository.addToFavourites(movie)
}