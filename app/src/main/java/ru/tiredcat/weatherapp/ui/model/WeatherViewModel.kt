package ru.tiredcat.weatherapp.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tiredcat.weatherapp.data.api.models.WeatherForecast
import ru.tiredcat.weatherapp.data.api.models.WeatherResponse
import ru.tiredcat.weatherapp.data.repository.WeatherRepository

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(
        val weather: WeatherResponse,
        val forecast: WeatherForecast) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var lastQuery: String = DEFAULT_QUERY

    init {
        loadWeather(lastQuery)
    }

    fun loadWeather(query: String) {
        lastQuery = query
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            val weatherDeferred = async { repository.getWeather(query.trim()) }
            val forecastDeferred = async { repository.getWeatherForecast(query.trim()) }

            val weatherResult = runCatching { weatherDeferred.await() }
            val forecastResult = runCatching { forecastDeferred.await() }

            val newState = when {
                weatherResult.isFailure && forecastResult.isFailure -> {
                    WeatherUiState.Error("Не удалось загрузить данные")
                }

                weatherResult.isFailure -> {
                    WeatherUiState.Error(weatherResult.exceptionOrNull()?.message ?: "Ошибка погоды")
                }
                else -> {
                    WeatherUiState.Success(
                        weather = weatherResult.getOrThrow(),
                        forecast = forecastResult.getOrThrow()
                    )
                }
            }
        }
    }

    fun refresh() = loadWeather(lastQuery)

    companion object {
        const val DEFAULT_QUERY = "Tomsk"
    }
}
