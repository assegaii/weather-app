package ru.tiredcat.weatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.tiredcat.weatherapp.data.repository.LocationRepository
import ru.tiredcat.weatherapp.data.repository.WeatherRepository
import ru.tiredcat.weatherapp.data.source.api.RetrofitInstance
import ru.tiredcat.weatherapp.ui.screens.WeatherMainScreen
import ru.tiredcat.weatherapp.ui.screens.WeeklyWeatherScreen
import ru.tiredcat.weatherapp.ui.theme.WeatherAppTheme
import ru.tiredcat.weatherapp.ui.viewmodel.WeatherViewModel
import ru.tiredcat.weatherapp.utils.SettingsManager


sealed class Routes (val route: String){
    object Home: Routes("home")
    object WeeklyWeather: Routes("weekly-weather")
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as WeatherApp
        val repository = app.weatherRepository
        val location = app.locationRepository
        val settings = app.settingsManager

        setContent {
            //Навигация
            val navController = rememberNavController()


            val factory = remember {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return WeatherViewModel(repository) as T
                    }
                }
            }

            val viewModel: WeatherViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current

            var hasLocationPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                )
            }

            LaunchedEffect(hasLocationPermission) {
                if (hasLocationPermission) {
                    try {
                        val location = location.getCurrentLocation()
                        val query = "${location.lat},${location.lon}"
                        settings.saveLastQuery(query)
                        viewModel.loadWeather(query)
                    } catch (e: Exception) {
                        viewModel.loadWeather(settings.getLastQuery())
                    }
                } else {
                    viewModel.loadWeather(settings.getLastQuery())
                }
            }

            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = Routes.Home.route){
                        composable(Routes.Home.route) {
                            WeatherMainScreen(
                                modifier = Modifier.padding(innerPadding),
                                uiState = uiState,
                                onSelectDay = { viewModel.selectDay(it) },
                                onRetry = { viewModel.refresh() },
                                onNavigateToWeekly = { navController.navigate(Routes.WeeklyWeather.route) }
                            )
                        }
                        composable(Routes.WeeklyWeather.route){
                            WeeklyWeatherScreen(
                                modifier = Modifier.padding(innerPadding),
                                uiState = uiState,
                                onBack = { navController.popBackStack() },
                                onRetry = { viewModel.refresh() }
                            )
                        }
                    }
                }
            }
        }
    }
}

