package ru.tiredcat.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tiredcat.weatherapp.ui.components.GeolocationText
import ru.tiredcat.weatherapp.ui.components.WeatherDetails
import ru.tiredcat.weatherapp.ui.components.WeatherToday
import ru.tiredcat.weatherapp.ui.components.formatApiLocalDate
import ru.tiredcat.weatherapp.ui.model.WeatherUiState

@Composable
fun WeatherMainScreen(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onRetry: () -> Unit,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF4CA1AF), Color(0xFF2C3E50)),
        )
    }
    Box(
        modifier = Modifier
            .background(gradientBrush)
            .fillMaxSize(),
    ) {
        when (uiState) {
            WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is WeatherUiState.Error -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = uiState.message,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    Button(
                        onClick = onRetry,
                        modifier = Modifier.padding(top = 16.dp),
                    ) {
                        Text("Повторить")
                    }
                }
            }

            is WeatherUiState.Success -> {

                //Погода на текущий день
                val weather = uiState.weather
                val current = weather.current
                val location = weather.location

                val forecast = uiState.forecast
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    GeolocationText(
                        city = location.name,
                        country = location.country,
                        dateLabel = formatApiLocalDate(location.localtime),
                    )
                    WeatherToday(
                        temperatureC = current.tempC.toInt(),
                        conditionText = current.condition.text,
                        conditionCode = current.condition.code,
                        isDay = current.isDay == 1L,
                        iconUrlFromApi = current.condition.icon,
                    )
                    WeatherDetails(
                        precipitation = "${current.precipMm.toInt()} мм",
                        wind = "${current.windKph.toInt()} км/ч",
                        humidity = "${current.humidity}%",
                    )
                }
            }
        }
    }
}
