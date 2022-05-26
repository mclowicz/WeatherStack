package com.mclowicz.weatherstack.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mclowicz.weatherstack.domain.model.Weather

@Entity
data class WeatherEntity(
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "temperature")
    val temperature: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun mapToWeather(weatherEntity: WeatherEntity): Weather =
    Weather(
        cityName = weatherEntity.cityName,
        temperature = weatherEntity.temperature,
        windSpeed = weatherEntity.windSpeed,
        pressure = weatherEntity.pressure,
        humidity = weatherEntity.humidity
    )