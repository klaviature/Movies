package com.example.movies.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Trailers(
    @SerializedName("trailers")
    val trailers: List<Trailer>? = null
) : Serializable
