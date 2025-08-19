package com.example.movies

import com.example.movies.data.database.FavouriteMovieEntity
import com.example.movies.data.database.WatchedMovieEntity
import com.example.movies.domain.model.MovieDeprecated

fun FavouriteMovieEntity.toMovie(): MovieDeprecated {
    return MovieDeprecated(
        id = this.id,
        name = this.name,
        alternativeName = this.alternativeName,
        type = this.type,
        year = this.year,
        description = this.description,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        poster = this.poster,
        rating = this.rating
    )
}

fun WatchedMovieEntity.toMovie(): MovieDeprecated {
    return MovieDeprecated(
        id = this.id,
        name = this.name,
        alternativeName = this.alternativeName,
        type = this.type,
        year = this.year,
        description = this.description,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        poster = this.poster,
        rating = this.rating
    )
}

fun MovieDeprecated.toFavouriteMovie(): FavouriteMovieEntity {
    return FavouriteMovieEntity(
        id = this.id,
        name = this.name,
        alternativeName = this.alternativeName,
        type = this.type,
        year = this.year,
        description = this.description,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        poster = this.poster,
        rating = this.rating
    )
}

fun MovieDeprecated.toWatchedMovie(): WatchedMovieEntity {
    return WatchedMovieEntity(
        id = this.id,
        name = this.name,
        alternativeName = this.alternativeName,
        type = this.type,
        year = this.year,
        description = this.description,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        poster = this.poster,
        rating = this.rating
    )
}