package ru.tiredcat.weatherapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tiredcat.weatherapp.data.source.local.entity.CityEntity
import ru.tiredcat.weatherapp.data.source.local.entity.CurrentWeatherEntity
import ru.tiredcat.weatherapp.data.source.local.entity.HourlyForecastEntity
import ru.tiredcat.weatherapp.data.source.local.entity.DailyForecastEntity

@Dao
interface WeatherDao {
    // Текущая погода
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather WHERE city_id = :cityId")
    fun getCurrentWeather(cityId: Long): Flow<CurrentWeatherEntity?>

    // Почасовой прогноз
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(forecast: List<HourlyForecastEntity>)

    @Query("SELECT * FROM hourly_forecast WHERE city_id = :cityId AND date = :date ORDER BY time ASC")
    fun getHourlyForecast(cityId: Long, date: Long): Flow<List<HourlyForecastEntity>>

    // Дневной прогноз
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecast(forecast: List<DailyForecastEntity>)

    @Query("SELECT * FROM daily_forecast WHERE city_id = :cityId ORDER BY date ASC")
    fun getDailyForecast(cityId: Long): Flow<List<DailyForecastEntity>>
}