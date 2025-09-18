package com.example.movies.di

import com.example.movies.BuildConfig
import com.example.movies.data.api.ApiKeyInterceptor
import com.example.movies.data.api.ApiServiceCoroutines
import com.example.movies.data.repository.ApiKeyProviderRepository
import com.example.movies.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(apiKeyProviderRepository: ApiKeyProviderRepository): ApiKeyInterceptor {
        return ApiKeyInterceptor(apiKeyProviderRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()
                )
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServiceCoroutines {
        return retrofit.create(ApiServiceCoroutines::class.java)
    }
}