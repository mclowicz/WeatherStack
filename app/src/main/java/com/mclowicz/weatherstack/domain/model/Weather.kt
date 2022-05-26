package com.mclowicz.weatherstack.domain.model

data class Weather(
    val cityName: String,
    val temperature: Int,
    val windSpeed: Int,
    val pressure: Int,
    val humidity: Int
)