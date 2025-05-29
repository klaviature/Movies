package com.example.movies.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Trailer(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
): Serializable
