package com.example.movies

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movies.data.database.MovieDao
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.api.model.MovieType
import com.example.movies.data.repository.MoviesRepositoryImplTest
import com.example.movies.domain.model.Image
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieRating
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

val testMovieIn1 = Movie(
    id = 123,
    name = "Avengers",
    type = MovieType.MOVIE,
    year = 2025,
    description = "Some nice ass fuck boys gang fights with the worst guys",
    status = "Creating",
    rating = MovieRating(8.0, 4.5),
    length = 142,
    ageRating = 18,
    logo = Image(null, null),
    poster = Image("https://someweb.com/image/123", null),
    backdrop = Image(null, "https://someweb.com/image/123"),
    videos = null,
    genres = listOf("Action", "Adventure"),
    countries = listOf("USA", "Russia"),
    reviewInfo = null,
    budget = null,
    fees = null,
    similarMovies = null,
    sequelsAndPrequels = null,
    top10 = null,
    top250 = null,
    isTicketsOnSale = false,
    totalSeriesLength = null,
    averageSeriesLength = null,
    isSeries = false
)

val testMovieOut1 = Movie(
    id = 123,
    name = "Avengers",
    type = MovieType.MOVIE,
    year = 2025,
    description = "Some nice ass fuck boys gang fights with the worst guys",
    status = null,
    rating = MovieRating(null, null),
    length = 142,
    ageRating = 18,
    logo = Image(null, null),
    poster = Image("https://someweb.com/image/123", null),
    backdrop = Image("https://someweb.com/image/123", null),
    videos = null,
    genres = listOf("Action", "Adventure"),
    countries = listOf("USA", "Russia"),
    reviewInfo = null,
    budget = null,
    fees = null,
    similarMovies = null,
    sequelsAndPrequels = null,
    top10 = null,
    top250 = null,
    isTicketsOnSale = false,
    totalSeriesLength = null,
    averageSeriesLength = null,
    isSeries = false
)

@RunWith(AndroidJUnit4::class)
class MovieRepositoryTest {
    private lateinit var dao: MovieDao
    private lateinit var db: MovieDatabase
    private lateinit var repository: MoviesRepositoryTest

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.movieDao()
        repository = MoviesRepositoryImplTest(dao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `movie db and repository works`() = runTest {
        val movie = testMovieIn1
        val requiredSteps = listOf("Loading", "Success")

        println("Adding movie to favourites")
        val currentAddToFavouritesSteps = mutableListOf<String>()
        repository.addMovieToFavourite(movie).collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentAddToFavouritesSteps.add("Loading")
                }
                is Result.Success -> {
                    currentAddToFavouritesSteps.add("Success")
                    Assert.assertEquals(requiredSteps, currentAddToFavouritesSteps)
                }
            }
        }

        println("Getting favourite movies")
        val currentGetFavouritesSteps = mutableListOf<String>()
        repository.getFavouriteMovies().collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentGetFavouritesSteps.add("Loading")
                }
                is Result.Success -> {
                    currentGetFavouritesSteps.add("Success")
                    assertTrue(it.data[0] == testMovieOut1)
                    assertTrue(currentGetFavouritesSteps == requiredSteps)
                }
            }
        }

        println("Getting movie details")
        val currentGetDetailsSteps = mutableListOf<String>()
        repository.getMovieDetailsFromLocal(movie.id).collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentGetDetailsSteps.add("Loading")
                }
                is Result.Success -> {
                    currentGetDetailsSteps.add("Success")
                    Assert.assertEquals(testMovieOut1, it.data)
                    Assert.assertEquals(requiredSteps, currentGetDetailsSteps)
                }
            }
        }

        println("Checking if movie is in favourites")
        val currentIsInFavouritesSteps = mutableListOf<String>()
        repository.isMovieInFavourite(movie.id).collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentIsInFavouritesSteps.add("Loading")
                }
                is Result.Success -> {
                    currentIsInFavouritesSteps.add("Success")
                    assertTrue(it.data)
                    Assert.assertEquals(requiredSteps, currentIsInFavouritesSteps)
                }
            }
        }

        println("Adding movie to watched")
        val currentAddToWatchedSteps = mutableListOf<String>()
        repository.addMovieToWatched(movie).collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentAddToWatchedSteps.add("Loading")
                }
                is Result.Success -> {
                    currentAddToWatchedSteps.add("Success")
                    Assert.assertEquals(requiredSteps, currentAddToWatchedSteps)
                }
            }
        }

        println("Getting watched movies")
        val currentGetWatchedSteps = mutableListOf<String>()
        repository.getWatchedMovies().collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentGetWatchedSteps.add("Loading")
                }
                is Result.Success -> {
                    currentGetWatchedSteps.add("Success")
                    Assert.assertEquals(testMovieOut1, it.data[0])
                    Assert.assertEquals(requiredSteps, currentGetWatchedSteps)
                }
            }
        }

        println("Checking if movie is in watched")
        val currentIsInWatchedSteps = mutableListOf<String>()
        repository.isMovieInWatched(movie.id).collect {
            when (it) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentIsInWatchedSteps.add("Loading")
                }
                is Result.Success -> {
                    currentIsInWatchedSteps.add("Success")
                    assertTrue(it.data)
                    Assert.assertEquals(requiredSteps, currentIsInWatchedSteps)
                }
            }
        }

        println("Removing movie from watched")
        val currentRemoveFromWatchedSteps = mutableListOf<String>()
        repository.removeMovieFromWatched(movie.id).collect { removeResult ->
            when (removeResult) {
                is Result.Error -> {
                    Assert.fail()
                }
                Result.Loading -> {
                    currentRemoveFromWatchedSteps.add("Loading")
                }
                is Result.Success -> {
                    currentRemoveFromWatchedSteps.add("Success")
                    Assert.assertEquals(requiredSteps, currentRemoveFromWatchedSteps)
                    repository.isMovieInWatched(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                                Assert.fail()
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.assertFalse(it.data)
                            }
                        }
                    }
                    repository.isMovieInFavourite(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                                Assert.fail()
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.assertTrue(it.data)
                            }
                        }
                    }
                    repository.getMovieDetailsFromLocal(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                                Assert.fail()
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.assertEquals(testMovieOut1, it.data)
                            }
                        }
                    }
                }
            }
        }

        println("Removing movie from favourites")
        val currentRemoveFromFavouritesSteps = mutableListOf<String>()
        repository.removeMovieFromFavourite(movie.id).collect { removeResult ->
            when (removeResult) {
                is Result.Error -> {
                    Assert.fail()
                }
                is Result.Loading -> {
                    currentRemoveFromFavouritesSteps.add("Loading")
                }
                is Result.Success -> {
                    currentRemoveFromFavouritesSteps.add("Success")
                    Assert.assertEquals(requiredSteps, currentRemoveFromFavouritesSteps)
                    repository.isMovieInFavourite(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                                Assert.fail()
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.assertFalse(it.data)
                            }
                        }
                    }
                    repository.isMovieInWatched(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                                Assert.fail()
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.assertFalse(it.data)
                            }
                        }
                    }
                    repository.getMovieDetailsFromLocal(movie.id).collect {
                        when (it) {
                            is Result.Error -> {
                            }
                            Result.Loading -> {}
                            is Result.Success -> {
                                Assert.fail()
                            }
                        }
                    }
                }
            }
        }
    }
}