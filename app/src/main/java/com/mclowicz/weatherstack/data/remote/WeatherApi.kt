package com.mclowicz.weatherstack.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/current")
    fun fetchWeather(
        @Query("query") query: String
    ) : Single<WeatherResponse>
}