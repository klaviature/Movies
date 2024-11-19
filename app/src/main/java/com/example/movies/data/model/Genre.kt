package com.example.movies.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genre(
    @SerializedName("name")
    val name: String
): Serializable {
    override fun toString(): String {
        return name.replaceFirstChar { it.uppercase() }
    }
}
