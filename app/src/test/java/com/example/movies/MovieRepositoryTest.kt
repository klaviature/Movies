package com.example.movies

import com.example.movies.data.api.model.MovieType
import com.example.movies.domain.model.Image
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieRating

val testMovie1 = Movie(
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

//class MovieRepositoryTest {
//    private lateinit var dao: MovieDao
//    private lateinit var db: MovieDatabase
//    private lateinit var repository: MoviesRepositoryTest
//
//    @Before
//    fun setup() {
//        db = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            MovieDatabase::class.java
//        )
//            .allowMainThreadQueries()
//            .build()
//        dao = db.movieDao()
//        repository = MoviesRepositoryImplTest(dao)
//    }
//}