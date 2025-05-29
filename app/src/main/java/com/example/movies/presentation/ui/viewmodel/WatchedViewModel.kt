package com.example.movies.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.movies.data.database.MovieDatabase
import com.example.movies.domain.model.Movie
import com.example.movies.toMovie

class WatchedViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = "WatchedViewModel"

    private val watchedMoviesDao =
        MovieDatabase.getInstance(getApplication<Application>().applicationContext).watchedMoviesDao()

    fun getMovies(): LiveData<List<Movie>> {
        return watchedMoviesDao.getWatchedMovies().map { it -> it.map { it.toMovie() } }
    }
}