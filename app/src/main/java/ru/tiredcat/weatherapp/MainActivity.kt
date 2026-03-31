package ru.tiredcat.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tiredcat.weatherapp.ui.screens.WeatherMainScreen
import ru.tiredcat.weatherapp.ui.theme.WeatherAppTheme
import ru.tiredcat.weatherapp.ui.model.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: WeatherViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherMainScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onRetry = { viewModel.refresh() },
                    )
                }
            }
        }
    }
}
