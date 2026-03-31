package ru.tiredcat.weatherapp.data.repository

import ru.tiredcat.weatherapp.BuildConfig
import ru.tiredcat.weatherapp.data.api.RetrofitInstance
import ru.tiredcat.weatherapp.data.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.api.models.WeatherResponse

class WeatherRepository{
    private val api = RetrofitInstance.api

    suspend fun getWeather(query: String): WeatherResponse{
        return api.getCurrentWeather(
            apiKey = BuildConfig.WEATHER_API_KEY,
            query = query
        )
    }

    suspend fun getWeatherForecast(query: String): WeatherForecast{
        return api.getWeatherForecast(
            apiKey = BuildConfig.WEATHER_API_KEY,
            query = query
        )
    }
}