package com.mclowicz.weatherstack.di

import com.mclowicz.weatherstack.data.local.WeatherDao
import com.mclowicz.weatherstack.data.remote.WeatherApi
import com.mclowicz.weatherstack.data.repository.WeatherRepositoryImpl
import com.mclowicz.weatherstack.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepositoryImpl(
        api: WeatherApi,
        dao: WeatherDao
    ) = WeatherRepositoryImpl(api = api, dao = dao)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ) : WeatherRepository = weatherRepositoryImpl
}