package ru.tiredcat.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tiredcat.weatherapp.R
import ru.tiredcat.weatherapp.ui.components.WeatherConditionIcon
import ru.tiredcat.weatherapp.ui.viewmodel.WeatherUiState


@Composable
fun WeeklyWeatherScreen(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
){
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF2C3E50), Color(0xFF4CA1AF)),
        )
    }
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
            val weeklyWeather = uiState.weeklyWeather
            
            Box(
                modifier = Modifier
                    .background(gradientBrush)
                    .fillMaxSize()
            ) {
                Column(modifier = modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row( verticalAlignment = Alignment.CenterVertically ) {
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.back_arrow),
                                contentDescription = "",
                                modifier = Modifier.size(44.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            Text(
                                text = "След. 3 дня",
                                fontSize = 28.sp,
                            )
                        }
                        Spacer(modifier = Modifier.width(44.dp))
                    }
                    weeklyWeather.map { dayWeather ->
                        WeeklyWeatherItem(date = dayWeather.dayOfWeek, tempC = dayWeather.temperatureC, conditionCode = dayWeather.conditionCode)
                    }

                }
            }
        }
    }


}


@Composable
fun WeeklyWeatherItem(date: String, tempC: Int, conditionCode: Long){

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(25))
            .background(Color.White.copy(alpha = 0.3f)

            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${tempC}°",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            WeatherConditionIcon(
                conditionCode = conditionCode,
                contentDescription = "",
                modifier = Modifier.size(44.dp),
            )
        }
    }
}