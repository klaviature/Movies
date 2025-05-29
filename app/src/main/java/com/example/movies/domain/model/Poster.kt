package com.example.movies.domain.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @ColumnInfo(name = "poster_url")
    @SerializedName("url")
    val url: String
): Serializable
