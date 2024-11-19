package com.example.movies.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Videos(
    @Embedded
    @SerializedName("trailers")
    val trailers: List<Trailer>? = null
) : Serializable
