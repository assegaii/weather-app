package ru.tiredcat.weatherapp.data.repository

import ru.tiredcat.weatherapp.BuildConfig
import ru.tiredcat.weatherapp.data.source.api.WeatherApi
import ru.tiredcat.weatherapp.data.source.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.source.api.models.WeatherResponse
import ru.tiredcat.weatherapp.data.source.local.dao.WeatherDao

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao
){


}