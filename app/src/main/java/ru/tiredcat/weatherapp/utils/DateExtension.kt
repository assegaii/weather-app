package ru.tiredcat.weatherapp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toDayOfWeek(): String {
    val date = LocalDate.parse(this)
    val formatter = DateTimeFormatter.ofPattern("EEEE", Locale("ru"))
    return date.format(formatter).replaceFirstChar { it.uppercase() }
}