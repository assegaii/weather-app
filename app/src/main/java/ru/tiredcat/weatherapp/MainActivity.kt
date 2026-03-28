package ru.tiredcat.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tiredcat.weatherapp.data.repository.WeatherRepository
import ru.tiredcat.weatherapp.ui.theme.WeatherAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var weatherText by mutableStateOf("Загрузка...")

        val repository = WeatherRepository()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val weather = repository.getWeather("Tomsk")
                val temp = weather.current.tempC
                val condition = weather.current.condition.text
                val result = "$temp°C, $condition"
                withContext(Dispatchers.Main) {
                    weatherText = result
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    weatherText = "Ошибка: ${e.message}"
                }
            }
        }
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = weatherText,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name!",
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    WeatherAppTheme {
        Greeting("")
    }
}