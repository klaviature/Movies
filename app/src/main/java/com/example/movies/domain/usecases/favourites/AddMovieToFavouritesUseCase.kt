package com.example.movies.domain.usecases.favourites

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.FavouritesRepository

class AddMovieToFavouritesUseCase(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(movie: Movie): Result<Unit> = repository.addToFavourites(movie)
}