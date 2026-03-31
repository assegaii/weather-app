package ru.tiredcat.weatherapp.ui.components

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tiredcat.weatherapp.R
import ru.tiredcat.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherDetails(
    precipitation: String,
    wind: String,
    humidity: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp),


    ) {
        WeatherDetailItem(
            icon = R.drawable.ic_rainfall_desc,
            title = "Осадки",
            value = precipitation
        )
        WeatherDetailItem(
            icon = R.drawable.ic_wind_desc,
            title = "Ветер",
            value = wind
        )
        WeatherDetailItem(
            icon = R.drawable.ic_humidity_desc,
            title = "Влажность",
            value = humidity
        )
    }
}

@Composable
fun WeatherDetailItem(icon: Int, title: String, value: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(26.dp))
        .background(Color.White.copy(alpha = 0.3f)),) {
        Row(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(62.dp),
                tint = Color.Unspecified

            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = value, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview(showBackground = true
)
@Composable
fun WeatherDetailsPreview(){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White.copy(alpha = 0.7f)),) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_rainfall_desc),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.Unspecified

            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Rainfall",
                fontSize = 7.sp
            )
            Spacer(modifier = Modifier.width(72.dp))
            Text(text = "3 см", fontSize = 7.sp)
        }
    }

}