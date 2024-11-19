package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String
) {
    override fun toString(): String {
        return name
    }
}