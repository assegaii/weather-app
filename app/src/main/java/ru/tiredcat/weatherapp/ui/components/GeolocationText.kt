package ru.tiredcat.weatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GeolocationText(
    city: String,
    country: String,
    dateLabel: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
    ) {
        Text(
            text = "$city,",
            fontSize = 20.sp,
        )
        Text(
            text = country,
            fontSize = 20.sp,
        )
        Text(
            text = dateLabel,
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

fun formatApiLocalDate(apiLocalTime: String): String {
    return runCatching {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val out = SimpleDateFormat("d MMMM yyyy", Locale.forLanguageTag("ru"))
        val parsed = parser.parse(apiLocalTime) ?: return@runCatching apiLocalTime
        out.format(parsed)
    }.getOrElse { apiLocalTime }
}
