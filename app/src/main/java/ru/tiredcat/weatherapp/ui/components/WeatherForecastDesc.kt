package ru.tiredcat.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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


@Composable
fun WeatherDayItem(){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(100))
        .background(Color.Red.copy(alpha = 0.3f))){
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Время"
            )
            //TODO Иконка погоды
            Icon(
                painter = painterResource(id = R.drawable.sleet),
                contentDescription = "",
                modifier = Modifier.size(42.dp),
                tint = Color.Unspecified,
            )
            Text(
                text = "19",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherForecastDesc(){
    val isToday = true

    Column() {
        //Навигация
        Row() {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (isToday) Color.Black else Color.Gray
                )
            ) {
                Text(text = "Today")
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (!isToday) Color.Black else Color.Gray
                )
            ) {
                Text(text = "Tomorrow")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Next 7 Days")
                Icon(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "",
                    modifier = Modifier.size(12.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Row() {
            WeatherDayItem()
        }
    }
}