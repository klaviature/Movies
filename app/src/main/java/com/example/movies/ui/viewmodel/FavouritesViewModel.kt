package com.example.movies.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.model.Movie
import com.example.movies.toMovie

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = "FavouritesViewModel"

    private val movieDao =
        MovieDatabase.getInstance(getApplication<Application>().applicationContext).favouriteMoviesDao()

    fun getMovies(): LiveData<List<Movie>> {
        return movieDao.getFavouriteMovies().map { it -> it.map { it.toMovie() } }
    }
}