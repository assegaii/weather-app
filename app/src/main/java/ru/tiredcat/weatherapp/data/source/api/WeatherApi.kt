package ru.tiredcat.weatherapp.data.source.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tiredcat.weatherapp.data.source.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.source.api.models.WeatherResponse


interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String = "no",
        @Query("lang") lang: String = "ru"
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int = 3
    ): WeatherForecast
}