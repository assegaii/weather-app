package ru.tiredcat.weatherapp.data

import ru.tiredcat.weatherapp.data.source.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.source.api.models.WeatherResponse
import ru.tiredcat.weatherapp.ui.components.formatApiLocalDate
import ru.tiredcat.weatherapp.ui.models.CurrentWeather
import ru.tiredcat.weatherapp.ui.models.DailyWeather
import ru.tiredcat.weatherapp.ui.models.HourlyWeather
import ru.tiredcat.weatherapp.utils.toDayOfWeek

fun WeatherForecast?.toDailyWeatherList(): List<DailyWeather>{
    return this?.forecast?.forecastday?.map { dayData ->
        DailyWeather(
            dayOfWeek = dayData.date.toDayOfWeek(),
            temperatureC = dayData.day.maxtempC.toInt(),
            iconUrlFromApi = dayData.day.condition.icon,
            conditionCode = dayData.day.condition.code
        )
    } ?: emptyList()
}

fun WeatherForecast?.toHourlyForecast(dayIndex: Int): List<HourlyWeather>{
    return this?.forecast?.forecastday?.getOrNull(dayIndex)?.hour?.map{
            hour ->
        HourlyWeather(
            time = hour.time.substringAfter(" "),
            temperatureC = hour.tempC.toInt(),
            conditionCode = hour.condition.code,
            iconUrlFromApi = hour.condition.icon
        )
    } ?: emptyList()
}

fun WeatherResponse.toCurrentWeather(): CurrentWeather{
    return CurrentWeather(
        city = this.location.name,
        country = this.location.country,
        temperatureC = this.current.tempC.toInt(),
        conditionText = this.current.condition.text,
        conditionCode = this.current.condition.code,
        isDay = this.current.isDay == 1L,
        dateLabel = formatApiLocalDate(this.location.localtime),
        iconUrlFromApi = this.current.condition.icon,
        precipitation = this.current.precipMm.toInt(),
        wind = this.current.windKph.toInt(),
        humidity = this.current.humidity
    )
}