package ru.tiredcat.weatherapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "daily_forecast",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("city_id")],
    primaryKeys = ["city_id", "date"]
)
data class DailyForecastEntity(
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    val date: Long,
    val iconCode: String,
    val temperatureMin: Double,
    val temperatureMax: Double
)