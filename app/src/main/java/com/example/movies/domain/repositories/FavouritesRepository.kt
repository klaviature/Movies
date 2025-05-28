package com.example.movies.domain.repositories

import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouriteMovies(): Flow<List<Movie>>

    suspend fun addToFavourites(movie: Movie): Result<Unit>

    suspend fun removeFromFavourites(movie: Movie): Result<Unit>

    suspend fun isFavourite(movie: Movie): Boolean
}