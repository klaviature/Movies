package com.example.movies.data.repository

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow

object MoviesRepositoryImpl : MoviesRepository {

    override suspend fun getRecommendedMovies(page: Int): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String, page: Int): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(id: Int): Movie {

    }
}