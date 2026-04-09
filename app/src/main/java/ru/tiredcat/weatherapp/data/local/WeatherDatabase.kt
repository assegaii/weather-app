package ru.tiredcat.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.tiredcat.weatherapp.data.local.dao.WeatherDao
import ru.tiredcat.weatherapp.data.local.entity.CityEntity
import ru.tiredcat.weatherapp.data.local.entity.CurrentWeatherEntity
import ru.tiredcat.weatherapp.data.local.entity.DailyForecastEntity
import ru.tiredcat.weatherapp.data.local.entity.HourlyForecastEntity

@Database(
    version = 1,
    entities = [
        CityEntity::class,
        CurrentWeatherEntity::class,
        DailyForecastEntity::class,
        HourlyForecastEntity::class
    ]
)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun getWeatherDao(): WeatherDao
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration() // ⚠️ Пока для разработки, потом замени на Migration
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}