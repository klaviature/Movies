package com.example.movies

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: String): String {
    val inputFormat =
        if (date.contains(".")) SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.US)
        else SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

    val dateParsed = inputFormat.parse(date)!!
    return outputFormat.format(dateParsed)
}

fun String.convertUtcToLocal(pattern: String): String {
    val utcTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    val localTime = utcTime.withZoneSameInstant(java.time.ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return localTime.format(formatter)
}