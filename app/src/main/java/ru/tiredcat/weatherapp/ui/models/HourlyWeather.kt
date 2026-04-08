package ru.tiredcat.weatherapp.ui.models

data class HourlyWeather(
    val time: String,
    val conditionCode: Long,
    val iconUrlFromApi: String,
    val temperatureC: Int
)