package com.example.movies.di

import com.example.movies.data.api.ApiFactoryCoroutines
import com.example.movies.data.api.ApiServiceCoroutines
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiServiceCoroutines {
        return ApiFactoryCoroutines.apiService
    }
}