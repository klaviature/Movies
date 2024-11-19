package com.example.movies

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: String): String {
    val inputFormat =
        if (date.contains(".")) SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.US)
        else SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

    val dateParsed = inputFormat.parse(date)!!
    return outputFormat.format(dateParsed)
}