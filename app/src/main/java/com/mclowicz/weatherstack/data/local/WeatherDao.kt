package com.mclowicz.weatherstack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity): Completable

    @Query("SELECT * FROM weatherentity WHERE city_name LIKE :query")
    fun getWeather(query: String): Single<WeatherEntity>
}