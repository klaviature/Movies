package com.example.movies.domain.model

data class CurrencyValue<T : Number>(
    val value: T,
    val currency: String? = null
)
