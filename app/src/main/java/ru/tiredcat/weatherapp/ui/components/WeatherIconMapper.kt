package ru.tiredcat.weatherapp.ui.components

import androidx.annotation.DrawableRes
import ru.tiredcat.weatherapp.R

object WeatherIconMapper {

    @DrawableRes
    fun localResForConditionCode(code: Long, isDay: Boolean): Int? = when (code) {
        1000L -> if (isDay) R.drawable.sunny else R.drawable.clear
        1006L -> R.drawable.cloudy
        1009L -> R.drawable.overcast
        1030L, 1135L, 1147L -> R.drawable.fog
        1063L -> if (isDay) R.drawable.partly_sunny else R.drawable.partly_cloudy
        1066L -> if (isDay) R.drawable.partly_sunny_flurries else R.drawable.mostly_cloudy_flurries
        1069L, 1204L, 1207L, 1249L, 1252L -> R.drawable.sleet
        1072L, 1168L, 1171L, 1198L,1201L, 1261L, 1264L,-> R.drawable.freezing_rain
        1087L, 1273L, 1276L -> if (isDay) R.drawable.partly_sunny_storms else R.drawable.partly_cloudy_storms
        1114L, 1210L, 1213L, 1216L, 1219L, 1222L, 1255L, 1258L, 1279L, 1282L -> R.drawable.snow
        1117L -> R.drawable.windy
        1150L, 1153L, 1180L, 1183L, 1186L, 1189L, 1192L, 1195L, 1240L, 1243L, 1246L-> R.drawable.rain
        1237L -> R.drawable.ice
        else -> R.drawable.cloudy
    }
}
