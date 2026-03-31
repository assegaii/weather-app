package ru.tiredcat.weatherapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.tiredcat.weatherapp.R

@Composable
fun WeatherConditionIcon(
    conditionCode: Long,
    isDay: Boolean,
    iconUrlFromApi: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val localRes = remember(conditionCode, isDay) {
        WeatherIconMapper.localResForConditionCode(code = conditionCode, isDay = isDay)
    }
    if (localRes != null) {
        Icon(
            painter = painterResource(id = localRes),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = Color.Unspecified,
        )
        return
    }

    val url = remember(iconUrlFromApi) {
        when {
            iconUrlFromApi.startsWith("//") -> "https:$iconUrlFromApi"
            iconUrlFromApi.startsWith("http") -> iconUrlFromApi
            iconUrlFromApi.isNotEmpty() -> "https://$iconUrlFromApi"
            else -> null
        }
    }
    val fallback = painterResource(R.drawable.sunny)
    if (url == null) {
        Icon(
            painter = fallback,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = Color.Unspecified,
        )
        return
    }

}
