package com.example.movies

import com.example.movies.data.database.FavouriteMovieEntity
import com.example.movies.data.database.WatchedMovieEntity
import com.example.movies.data.model.Movie

fun FavouriteMovieEntity.toMovie(): Movie {
    return Movie(
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

fun WatchedMovieEntity.toMovie(): Movie {
    return Movie(
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

fun Movie.toFavouriteMovie(): FavouriteMovieEntity {
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

fun Movie.toWatchedMovie(): WatchedMovieEntity {
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