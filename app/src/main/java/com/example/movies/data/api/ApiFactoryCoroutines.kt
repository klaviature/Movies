package com.example.movies.data.api

import com.example.movies.BuildConfig
import com.example.movies.data.repository.ApiKeyProviderRepository
import com.example.movies.data.repository.SettingsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//object ApiFactoryCoroutines {
//    private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(
//            Json.asConverterFactory(
//                "application/json; charset=utf-8".toMediaType()
//            )
//        )
//        .client(OkHttpClient.Builder()
//            .addInterceptor(ApiKeyInterceptor())
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//        )
//        .build()
//
//    val apiService: ApiServiceCoroutines = retrofit.create(ApiServiceCoroutines::class.java)
//}

class ApiKeyInterceptor @Inject constructor(
    private val apiKeyProviderRepository: ApiKeyProviderRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        val apiKey = runBlocking {
            apiKeyProviderRepository.apiKey.firstOrNull()
        }

        if (!apiKey.isNullOrEmpty()) {
            requestBuilder.addHeader("X-API-KEY", apiKey)
        }

        return chain.proceed(requestBuilder.build())
    }
}