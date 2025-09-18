package com.example.movies.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.movies.data.api.ApiServiceCoroutines
import com.example.movies.data.database.MovieDao
import com.example.movies.data.repository.ApiKeyProviderRepository
import com.example.movies.data.repository.MoviesRepositoryImplTest
import com.example.movies.data.repository.ReviewsRepositoryImpl
import com.example.movies.data.repository.SettingsRepository
import com.example.movies.domain.repositories.MoviesRepositoryTest
import com.example.movies.domain.repositories.ReviewsRepository
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
    fun provideMoviesRepository(movieDao: MovieDao, apiService: ApiServiceCoroutines): MoviesRepositoryTest {
        return MoviesRepositoryImplTest(movieDao, apiService)
    }

    @Provides
    @Singleton
    fun provideReviewsRepository(apiService: ApiServiceCoroutines): ReviewsRepository {
        return ReviewsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>,
        apiService: ApiServiceCoroutines
    ): SettingsRepository {
        return SettingsRepository(dataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideApiKeyProviderRepository(dataStore: DataStore<Preferences>): ApiKeyProviderRepository {
        return ApiKeyProviderRepository(dataStore)
    }
}