package com.example.movies.domain.usecases.favourites

import com.example.movies.domain.model.MovieDeprecated
import com.example.movies.domain.repositories.FavouritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritesMoviesUseCase(
    private val repository: FavouritesRepository
) {
    operator fun invoke(): Flow<List<MovieDeprecated>> = repository.getFavouriteMovies()
}