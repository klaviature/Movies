package com.example.movies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
    @SerializedName("id")
    val id: Int,
    @SerializedName("movieId")
    val movieId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("userRating")
    val userRating: Int,
    @SerializedName("reviewLikes")
    val likes: Int,
    @SerializedName("reviewDislikes")
    val dislikes: Int
) : Serializable
