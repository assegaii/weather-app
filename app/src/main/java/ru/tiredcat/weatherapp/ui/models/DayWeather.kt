package ru.tiredcat.weatherapp.ui.models

data class DayWeather(
    val dayOfWeek: String,
    val conditionCode: Long,
    val iconUrlFromApi: String,
    val temperatureC: Int
)