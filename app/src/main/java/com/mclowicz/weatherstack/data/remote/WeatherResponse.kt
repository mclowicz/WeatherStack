package com.mclowicz.weatherstack.data.remote

import com.google.gson.annotations.SerializedName
import com.mclowicz.weatherstack.data.local.WeatherEntity

data class WeatherResponse(
    val location: LocationResponse,
    val current: CurrentResponse,
    val success: Boolean?
)

data class LocationResponse(
    val name: String,
    val country: String,
    @SerializedName("localtime")
    val localTime: String
)

data class CurrentResponse(
    val temperature: Int,
    @SerializedName("wind_speed")
    val windSpeed: Int,
    val pressure: Int,
    val humidity: Int
)

fun mapToWeatherEntity(weatherResponse: WeatherResponse): WeatherEntity =
    WeatherEntity(
        cityName = weatherResponse.location.name,
        temperature = weatherResponse.current.temperature,
        windSpeed = weatherResponse.current.windSpeed,
        pressure = weatherResponse.current.pressure,
        humidity = weatherResponse.current.humidity
    )