package com.mclowicz.weatherstack.di

import android.content.Context
import androidx.room.Room
import com.mclowicz.weatherstack.data.local.WeatherDao
import com.mclowicz.weatherstack.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideOrganizerDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase = Room.databaseBuilder(
        context, WeatherDatabase::class.java,
        "weather-database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideWeatherDao(
        weatherDatabase: WeatherDatabase
    ): WeatherDao = weatherDatabase.weatherDao()
}