package com.mclowicz.weatherstack.domain.repository

import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.remote.WeatherResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun fetchWeatherFromRemote(query: String) : Single<WeatherResponse>

    fun getWeatherFromLocal(query: String): Single<WeatherEntity>

    fun saveWeather(weatherEntity: WeatherEntity): Completable
}