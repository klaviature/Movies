package com.example.movies.domain.repositories

import com.example.movies.domain.model.MovieDeprecated
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouriteMovies(): Flow<List<MovieDeprecated>>

    suspend fun addToFavourites(movie: MovieDeprecated): Result<Unit>

    suspend fun removeFromFavourites(movie: MovieDeprecated): Result<Unit>

    suspend fun isFavourite(movie: MovieDeprecated): Boolean
}