package ru.tiredcat.weatherapp

import android.app.Application
import ru.tiredcat.weatherapp.data.repository.LocationRepository
import ru.tiredcat.weatherapp.data.repository.WeatherRepository
import ru.tiredcat.weatherapp.data.source.api.RetrofitInstance
import ru.tiredcat.weatherapp.data.source.api.WeatherApi
import ru.tiredcat.weatherapp.data.source.local.WeatherDatabase
import ru.tiredcat.weatherapp.data.source.local.dao.WeatherDao
import ru.tiredcat.weatherapp.utils.SettingsManager

class WeatherApp: Application(){
    val database: WeatherDatabase by lazy { WeatherDatabase.getInstance(this) }
    val weatherDao: WeatherDao by lazy { database.getWeatherDao() }
    val weatherApi: WeatherApi by lazy { RetrofitInstance.api }
    val weatherRepository: WeatherRepository by lazy { WeatherRepository(weatherApi, weatherDao) }
    val locationRepository: LocationRepository by lazy { LocationRepository(this) }
    val settingsManager: SettingsManager by lazy { SettingsManager(this) }
}