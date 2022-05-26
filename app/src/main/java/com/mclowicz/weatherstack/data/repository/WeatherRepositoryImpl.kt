package com.mclowicz.weatherstack.data.repository

import androidx.room.rxjava3.EmptyResultSetException
import com.mclowicz.weatherstack.data.local.WeatherDao
import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.remote.WeatherApi
import com.mclowicz.weatherstack.data.remote.WeatherResponse
import com.mclowicz.weatherstack.data.remote.mapToWeatherEntity
import com.mclowicz.weatherstack.domain.repository.WeatherRepository
import com.mclowicz.weatherstack.presentation.detail.CustomException
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override fun fetchWeatherFromRemote(query: String): Single<WeatherResponse> {
        return api.fetchWeather(query = query)
    }

    override fun getWeatherFromLocal(query: String): Single<WeatherEntity> {
        return dao.getWeather(query = query)
    }

    override fun saveWeather(weatherEntity: WeatherEntity): Completable {
        return dao.insert(weatherEntity = weatherEntity)
    }

    override fun searchQuery(query: String): Observable<WeatherEntity> {
        return getWeatherFromLocal(query = query).flatMapObservable { fromLocal ->
            Observable.just(fromLocal)
        }.onErrorResumeNext { throwable ->
            when (throwable) {
                is EmptyResultSetException -> {
                    fetchWeatherFromRemote(query = query).flatMapObservable { weatherResponse ->
                        if (weatherResponse.success != null) {
                            Observable.error(CustomException.NotFoundException())
                        } else {
                            val weatherEntity =
                                mapToWeatherEntity(weatherResponse = weatherResponse)
                            Observable.just(weatherEntity)
                        }
                    }
                        .onErrorResumeNext {
                            when (it) {
                                is CustomException.NotFoundException -> {
                                    Observable.error(CustomException.NotFoundException())
                                }
                                else -> {
                                    Observable.error(CustomException.NoInternetException())
                                }
                            }
                        }
                }
                else -> {
                    Observable.error(throwable)
                }
            }
        }
    }
}