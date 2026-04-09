package ru.tiredcat.weatherapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "hourly_forecast",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("city_id"), Index("date")],
    primaryKeys = ["city_id", "date", "time"]
)
data class HourlyForecastEntity(
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    val date: Long,
    val time: Long,
    val iconCode: String,
    val temperature: Double
)