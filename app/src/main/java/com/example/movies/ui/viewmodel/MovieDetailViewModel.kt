package com.example.movies.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.data.api.kinopoisk.ApiFactory
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.model.Movie
import com.example.movies.data.model.Review
import com.example.movies.data.model.ReviewResponse
import com.example.movies.toFavouriteMovie
import com.example.movies.toMovie
import com.example.movies.toWatchedMovie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "MovieDetailViewModel"

    private val favouriteMoviesDao =
        MovieDatabase.getInstance(getApplication<Application>().applicationContext)
            .favouriteMoviesDao()

    private val watchedMoviesDao =
        MovieDatabase.getInstance(getApplication<Application>().applicationContext)
            .watchedMoviesDao()

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> = _movie

    private val _isInFavourites: MutableLiveData<Boolean> = MutableLiveData(false)
    val isInFavourites: LiveData<Boolean> = _isInFavourites

    private val _isInWatched: MutableLiveData<Boolean> = MutableLiveData(false)
    val isInWatched: LiveData<Boolean> = _isInWatched

    private val _reviews: MutableLiveData<List<Review>> = MutableLiveData()
    val reviews: LiveData<List<Review>> = _reviews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentReviewsPage = 1
    private var currentReviewsPageCount = 0

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadMovie(id: Int) {
        loadMovieFromFavourites(id)
        loadMovieFromWatched(id)
        loadMovieFromApi(id)
        loadReviewsFromApi(id, currentReviewsPage)
    }

    private fun loadMovieFromFavourites(id: Int) {
        val disposable =
            favouriteMoviesDao.getFavouriteMovie(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnSuccess { favouriteMovie ->
                    _movie.postValue(favouriteMovie.toMovie())
                    _isInFavourites.postValue(true)
                    Log.d(TAG, "Loaded from favourites")
                }.subscribe()
        compositeDisposable.add(disposable)
    }

    private fun loadMovieFromWatched(id: Int) {
        val disposable =
            watchedMoviesDao.getWatchedMovie(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnSuccess { watchedMovie ->
                    _movie.postValue(watchedMovie.toMovie())
                    _isInWatched.postValue(true)
                    Log.d(TAG, "Loaded from watched")
                }.subscribe()
        compositeDisposable.add(disposable)
    }

    private fun loadMovieFromApi(id: Int) {
        val disposable = ApiFactory.apiService.loadMovie(id).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.postValue(true) }
            .doAfterTerminate { _isLoading.postValue(false) }
            .subscribe(
                { movie ->
                    _movie.value = movie
                    Log.d(TAG, "Loaded from API")
                },
                { throwable -> Log.d(TAG, throwable.toString()) }
            )
        compositeDisposable.add(disposable)
    }

    private fun loadReviewsFromApi(id: Int, page: Int = 1) {
        val disposable =
            ApiFactory.apiService.loadReviews(id, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.postValue(true) }
                .doAfterTerminate { _isLoading.postValue(false) }
                .map { reviewResponse: ReviewResponse ->
                    currentReviewsPageCount = reviewResponse.pageCount
                    reviewResponse.reviews
                }.subscribe({ reviews ->
                    val currentReviews = _reviews.value?.toMutableList() ?: mutableListOf()
                    currentReviews.addAll(reviews)
                    _reviews.value = currentReviews
                }, { throwable ->
                    Log.d(TAG, throwable.toString())
                })
        compositeDisposable.add(disposable)
    }

    fun loadReviewsNextPage() {
        if (currentReviewsPage != currentReviewsPageCount) {
            currentReviewsPage++
            loadReviewsFromApi(_movie.value?.id ?: -1, currentReviewsPage)
        }
    }

    fun toggleFavourite(movie: Movie, action: (Boolean) -> Unit = { }) {
        var inFavouritesToAction = false
        val disposable =
            favouriteMoviesDao.isMovieInFavourites(movie.id)
                .flatMapCompletable { inFavourites ->
                    if (inFavourites) {
                        favouriteMoviesDao.removeMovieFromFavourites(movie.toFavouriteMovie())
                            .doOnComplete {
                                _isInFavourites.postValue(false)
                                inFavouritesToAction = false
                            }
                    } else {
                        favouriteMoviesDao.addMovieToFavourites(movie.toFavouriteMovie())
                            .doOnComplete {
                                _isInFavourites.postValue(true)
                                inFavouritesToAction = true
                            }
                    }
                }
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    action(inFavouritesToAction)
                }
        compositeDisposable.add(disposable)
    }

    fun toggleWatched(movie: Movie, action: (Boolean) -> Unit = {  }) {
        var inWatchedToAction = false
        val disposable = watchedMoviesDao.isMovieWatched(movie.id)
            .flatMapCompletable { isWatched ->
                if (isWatched) {
                    watchedMoviesDao.removeMovieFromWatched(movie.id)
                        .doOnComplete {
                            _isInWatched.postValue(false)
                            inWatchedToAction = false
                        }
                } else {
                    watchedMoviesDao.addMovieToWatched(movie.toWatchedMovie())
                        .doOnComplete {
                            _isInWatched.postValue(true)
                            inWatchedToAction = true
                        }
                }
            }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                action(inWatchedToAction)
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}