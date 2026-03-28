package ru.tiredcat.weatherapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tiredcat.weatherapp.data.api.models.WeatherResponse


interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String = "no",
        @Query("lang") lang: String = "ru"
    ): WeatherResponse

}