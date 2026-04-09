package ru.tiredcat.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tiredcat.weatherapp.data.local.entity.CityEntity
import ru.tiredcat.weatherapp.data.local.entity.CurrentWeatherEntity
import ru.tiredcat.weatherapp.data.local.entity.HourlyForecastEntity
import ru.tiredcat.weatherapp.data.local.entity.DailyForecastEntity

@Dao
interface WeatherDao {
    // Добавить город
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: CityEntity): Long
    // Получить город по координатам
    @Query("SELECT * FROM cities WHERE lat = :lat AND lon = :lon")
    suspend fun getCityByCoordinates(lat: Double, lon: Double): CityEntity?

    // Добавить текущую погоду
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)
    // Получить текущую погоду
    @Query("SELECT * FROM current_weather WHERE city_id = :cityId")
    fun getCurrentWeather(cityId: Long): Flow<CurrentWeatherEntity?>

    // Добавить почасовой прогноз
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(forecast: List<HourlyForecastEntity>)
    // Получить почасовой прогноз
    @Query("SELECT * FROM hourly_forecast WHERE city_id = :cityId AND date = :date ORDER BY time ASC")
    fun getHourlyForecast(cityId: Long, date: Long): Flow<List<HourlyForecastEntity>>

    // Удалить старый почасовой прогноз
    @Query("DELETE FROM hourly_forecast WHERE date < :expiryDate")
    suspend fun deleteOldHourlyForecast(expiryDate: Long)

    // Добавить недельный прогноз
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecast(forecast: List<DailyForecastEntity>)
    // Получить недельный прогноз
    @Query("SELECT * FROM daily_forecast WHERE city_id = :cityId ORDER BY date ASC")
    fun getDailyForecast(cityId: Long): Flow<List<DailyForecastEntity>>
}