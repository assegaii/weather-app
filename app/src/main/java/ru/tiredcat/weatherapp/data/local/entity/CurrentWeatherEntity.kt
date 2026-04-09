package ru.tiredcat.weatherapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_weather",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("city_id")]
)
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    val temperature: Double,
    val description: String,
    val iconCode: String,
    val date: Long,
    val precipitation: Float,
    val windSpeed: Float,
    val humidity: Int
)