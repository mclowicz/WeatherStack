package com.mclowicz.weatherstack.data.repository

import com.mclowicz.weatherstack.data.local.WeatherDao
import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.remote.WeatherApi
import com.mclowicz.weatherstack.data.remote.WeatherResponse
import com.mclowicz.weatherstack.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override fun fetchWeatherFromRemote(query: String) : Single<WeatherResponse> {
        return api.fetchWeather(query = query)
    }

    override fun getWeatherFromLocal(query: String): Single<WeatherEntity> {
        return dao.getWeather(query = query)
    }

    override fun saveWeather(weatherEntity: WeatherEntity): Completable {
        return dao.insert(weatherEntity = weatherEntity)
    }
}