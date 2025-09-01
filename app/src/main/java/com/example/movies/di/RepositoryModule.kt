package com.example.movies.di

import com.example.movies.data.api.ApiServiceCoroutines
import com.example.movies.data.database.MovieDao
import com.example.movies.data.repository.MoviesRepositoryImplTest
import com.example.movies.data.repository.ReviewsRepositoryImpl
import com.example.movies.domain.repositories.MoviesRepositoryTest
import com.example.movies.domain.repositories.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(movieDao: MovieDao): MoviesRepositoryTest {
        return MoviesRepositoryImplTest(movieDao)
    }

    @Provides
    @Singleton
    fun provideReviewsRepository(apiService: ApiServiceCoroutines): ReviewsRepository {
        return ReviewsRepositoryImpl(apiService)
    }
}