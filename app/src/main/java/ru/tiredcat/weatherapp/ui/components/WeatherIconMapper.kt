package ru.tiredcat.weatherapp.ui.components

import androidx.annotation.DrawableRes
import ru.tiredcat.weatherapp.R

object WeatherIconMapper {

    @DrawableRes
    fun localResForConditionCode(code: Long, isDay: Boolean): Int? = when (code) {
        1000L -> if (isDay) R.drawable.sunny else R.drawable.clear
        1006L -> R.drawable.cloudy
        1009L -> R.drawable.overcast
        1030L -> R.drawable.fog
        1063L -> if (isDay) R.drawable.partly_sunny else R.drawable.partly_cloudy
        1066L -> if (isDay) R.drawable.partly_sunny_flurries else R.drawable.mostly_cloudy_flurries
        1069L, 1204L, 1207L, 1249L, 1252L -> R.drawable.sleet
        else -> null
    }
}
