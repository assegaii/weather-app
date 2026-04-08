package ru.tiredcat.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tiredcat.weatherapp.R
import ru.tiredcat.weatherapp.ui.models.HourlyWeather
import ru.tiredcat.weatherapp.ui.viewmodel.Day
import ru.tiredcat.weatherapp.ui.viewmodel.WeatherUiState


@Composable
fun WeatherDayItem(
    time: String,
    conditionCode: Long,
    iconUrlFromApi: String,
    temperatureC: Int){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(100))
        .background(Color.White.copy(alpha = 0.3f))){
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                fontSize = 16.sp
            )
            //TODO Иконка погоды
            WeatherConditionIcon(
                conditionCode = conditionCode,
                iconUrlFromApi = iconUrlFromApi,
                contentDescription = "",
                modifier = Modifier.size(62.dp),
            )
            Row() {
                Text(
                    text = temperatureC.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Text(
                    text = "°",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}


@Composable
fun WeatherForecastDesc(
    onSelectDay: (Day) -> Unit,
    state: WeatherUiState.Success,
    forecastItems: List<HourlyWeather>,
    onNavigateToWeekly: () -> Unit,
    ){

    val isToday = state.selectedDay == Day.TODAY


    Column() {
        //Навигация
        Row() {
            Button(
                onClick = {onSelectDay(Day.TODAY)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (isToday) Color.Black else Color.Gray
                )
            ) {
                Text(
                    text = "Сегодня",
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {onSelectDay(Day.TOMORROW)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (!isToday) Color.Black else Color.Gray

                )
            ) {
                Text(
                    text = "Завтра",
                    fontWeight = if (!isToday) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNavigateToWeekly,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "След. 3 дня",
                    fontSize = 18.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "",
                    modifier = Modifier.size(12.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(forecastItems) { item ->
                WeatherDayItem(
                    time = item.time,
                    conditionCode = item.conditionCode,
                    iconUrlFromApi = item.iconUrlFromApi,
                    temperatureC = item.temperatureC
                )
            }
        }
    }
}