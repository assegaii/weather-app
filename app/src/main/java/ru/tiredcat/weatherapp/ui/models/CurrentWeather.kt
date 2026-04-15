package ru.tiredcat.weatherapp.ui.models

data class CurrentWeather(
    val city: String,
    val country: String,
    val temperatureC: Int,
    val conditionText: String,
    val conditionCode: Long,
    val isDay: Boolean,
    val dateLabel: String,
    val iconUrlFromApi: String,
    val precipitation: Int,
    val wind: Int,
    val humidity: Long
)