package com.example.movies.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("kp")
    val kp: Double,
    @SerializedName("imdb")
    val imdb: Double
): Serializable
