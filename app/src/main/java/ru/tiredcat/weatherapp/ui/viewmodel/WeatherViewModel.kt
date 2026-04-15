package ru.tiredcat.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tiredcat.weatherapp.data.repository.LocationRepository
import ru.tiredcat.weatherapp.data.repository.WeatherRepository
import ru.tiredcat.weatherapp.data.toCurrentWeather
import ru.tiredcat.weatherapp.data.toDailyWeatherList
import ru.tiredcat.weatherapp.data.toHourlyForecast
import ru.tiredcat.weatherapp.ui.models.CurrentWeather
import ru.tiredcat.weatherapp.ui.models.DailyWeather
import ru.tiredcat.weatherapp.ui.models.HourlyWeather
import ru.tiredcat.weatherapp.utils.SettingsManager

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(
        val currentWeather: CurrentWeather,
        val selectedDay: Day = Day.TODAY,
        val todayHourly: List<HourlyWeather> = emptyList(),
        val tomorrowHourly: List<HourlyWeather> = emptyList(),
        val weeklyWeather: List<DailyWeather> = emptyList()) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

enum class Day{ TODAY, TOMORROW}

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()



    fun loadWeather(query: String) {

        viewModelScope.launch()
        {
            _uiState.value = WeatherUiState.Loading

        try {
            val weatherDeferred = async { repository.getWeather(query.trim()) }
            val forecastDeferred = async { repository.getWeatherForecast(query.trim()) }

            val weatherResult = runCatching { weatherDeferred.await() }
            val forecastResult = runCatching { forecastDeferred.await() }

            val weather = weatherResult.getOrNull()
            val forecast = forecastResult.getOrNull()


            val currentWeather = weather?.toCurrentWeather()

            val todayHourly = forecast.toHourlyForecast(0)
            val tomorrowHourly = forecast.toHourlyForecast(1)

            val weeklyWeather = forecast.toDailyWeatherList()

            val newState = if (currentWeather != null)
            {
                WeatherUiState.Success(
                    currentWeather = currentWeather,
                    todayHourly = todayHourly,
                    tomorrowHourly = tomorrowHourly,
                    weeklyWeather = weeklyWeather
                )
            } else WeatherUiState.Error(weatherResult.exceptionOrNull()?.message ?: "Ошибка погоды")

            _uiState.value = newState
        } catch (e: Exception){
            _uiState.value = WeatherUiState.Error(e.message ?: "Ошибка геолокации")
        }
        }
    }



    fun selectDay(day: Day) {
        val currentState = _uiState.value
        if (currentState is WeatherUiState.Success) {
            _uiState.value = currentState.copy(selectedDay = day)
        }
    }

    fun refresh() {
    }



}
