package ru.tiredcat.weatherapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.tiredcat.weatherapp.data.source.local.dao.WeatherDao
import ru.tiredcat.weatherapp.data.source.local.entity.CityEntity
import ru.tiredcat.weatherapp.data.source.local.entity.CurrentWeatherEntity
import ru.tiredcat.weatherapp.data.source.local.entity.DailyForecastEntity
import ru.tiredcat.weatherapp.data.source.local.entity.HourlyForecastEntity

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
                    "weather_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}