package com.mclowicz.weatherstack.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.rxjava3.EmptyResultSetException
import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.local.mapToWeather
import com.mclowicz.weatherstack.data.remote.mapToWeatherEntity
import com.mclowicz.weatherstack.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState> = _viewState

    fun searchQuery(query: String) {
        disposable.add(
            weatherRepository.getWeatherFromLocal(query = query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _viewState.value = DetailViewState
                        .Content(data = mapToWeather(weatherEntity = response))
                }, { throwable ->
                    when (throwable) {
                        is EmptyResultSetException -> {
                            fetchWeather(query = query)
                        }
                        else -> {
                            _viewState.value =
                                DetailViewState.Error(message = throwable.localizedMessage)
                        }
                    }
                })
        )
    }

    private fun fetchWeather(query: String) {
        disposable.add(
            weatherRepository.fetchWeatherFromRemote(query = query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _viewState.value = DetailViewState.Loading
                }
                .subscribe({ response ->
                    if (response.success != null) {
                        _viewState.value = DetailViewState.Error(message = "City not found.")
                    } else {
                        val weatherEntity = mapToWeatherEntity(weatherResponse = response)
                        saveWeather(weatherEntity = weatherEntity)
                        _viewState.value = DetailViewState.Content(data = mapToWeather(weatherEntity))
                    }
                }, { throwable ->
                    _viewState.value =
                        DetailViewState.Error(
                            message = "Please, check your internet connection and try again."
                        )
                })
        )
    }

    private fun saveWeather(weatherEntity: WeatherEntity) {
        disposable.add(
            weatherRepository.saveWeather(weatherEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    Log.d("Weatherstack", "saved")
                }
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}