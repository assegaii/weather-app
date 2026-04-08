package ru.tiredcat.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tiredcat.weatherapp.data.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.api.models.WeatherResponse
import ru.tiredcat.weatherapp.data.repository.LocationRepository
import ru.tiredcat.weatherapp.data.repository.WeatherRepository
import ru.tiredcat.weatherapp.ui.models.DayWeather
import ru.tiredcat.weatherapp.ui.models.HourlyWeather
import ru.tiredcat.weatherapp.utils.SettingsManager
import ru.tiredcat.weatherapp.utils.toDayOfWeek

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(
        val weather: WeatherResponse,
        val forecast: WeatherForecast,
        val selectedDay: Day = Day.TODAY,
        val todayHourly: List<HourlyWeather> = emptyList(),
        val tomorrowHourly: List<HourlyWeather> = emptyList(),
        val weeklyWeather: List<DayWeather> = emptyList()) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

enum class Day{ TODAY, TOMORROW}

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository(),
    private val locationRepository: LocationRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var lastQuery = settingsManager.getLastQuery()


    fun loadWeather(query: String) {

        viewModelScope.launch()
        {
            _uiState.value = WeatherUiState.Loading

        try {
            val weatherDeferred = async { repository.getWeather(query.trim()) }
            val forecastDeferred = async { repository.getWeatherForecast(query.trim()) }

            val weatherResult = runCatching { weatherDeferred.await() }
            val forecastResult = runCatching { forecastDeferred.await() }

            val forecast = forecastResult.getOrNull()

            val todayHourly = forecast?.forecast?.forecastday?.getOrNull(0)?.hour?.map { hour ->
                HourlyWeather(
                    time = hour.time.substringAfter(" "),
                    temperatureC = hour.tempC.toInt(),
                    conditionCode = hour.condition.code,
                    iconUrlFromApi = hour.condition.icon,
                )
            } ?: emptyList()

            val tomorrowHourly = forecast?.forecast?.forecastday?.getOrNull(1)?.hour?.map { hour ->
                HourlyWeather(
                    time = hour.time.substringAfter(" "),
                    temperatureC = hour.tempC.toInt(),
                    conditionCode = hour.condition.code,
                    iconUrlFromApi = hour.condition.icon
                )
            } ?: emptyList()

            val weeklyWeather = forecastResult.getOrNull()?.let { forecast ->
                mapToDailyForecasts(forecast)
            } ?: emptyList()

            val newState = when {
                weatherResult.isFailure && forecastResult.isFailure -> {
                    WeatherUiState.Error("Не удалось загрузить данные")
                }

                weatherResult.isFailure -> {
                    WeatherUiState.Error(weatherResult.exceptionOrNull()?.message ?: "Ошибка погоды")
                }
                forecastResult.isFailure -> {
                    WeatherUiState.Error(forecastResult.exceptionOrNull()?.message ?: "Ошибка погоды")
                }
                else -> {
                    WeatherUiState.Success(
                        weather = weatherResult.getOrThrow(),
                        forecast = forecastResult.getOrThrow(),
                        todayHourly = todayHourly,
                        tomorrowHourly = tomorrowHourly,
                        weeklyWeather = weeklyWeather

                    )
                }
            }

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
        loadWeather(lastQuery)
    }



    private fun mapToDailyForecasts(forecast: WeatherForecast): List<DayWeather> {
        return forecast.forecast.forecastday.map { dayData ->
            DayWeather(
                dayOfWeek = dayData.date.toDayOfWeek(),
                temperatureC = dayData.day.maxtempC.toInt(),
                conditionCode = dayData.day.condition.code,
                iconUrlFromApi = dayData.day.condition.icon,
            )
        }
    }
}
