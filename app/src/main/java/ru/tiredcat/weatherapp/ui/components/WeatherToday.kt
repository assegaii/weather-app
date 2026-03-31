package ru.tiredcat.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherToday(
    temperatureC: Int,
    conditionText: String,
    conditionCode: Long,
    isDay: Boolean,
    iconUrlFromApi: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        WeatherConditionIcon(
            conditionCode = conditionCode,
            isDay = isDay,
            iconUrlFromApi = iconUrlFromApi,
            contentDescription = conditionText,
            modifier = Modifier.size(172.dp),
        )
        Spacer(modifier = Modifier.width(18.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = temperatureC.toString(),
                    fontSize = 92.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = conditionText,
                    fontSize = 34.sp,
                )
            }
            Text(
                text = "°c",
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherTodayPreview() {
    WeatherToday(
        temperatureC = 19,
        conditionText = "Sunny",
        conditionCode = 1000L,
        isDay = true,
        iconUrlFromApi = "",
    )
}
