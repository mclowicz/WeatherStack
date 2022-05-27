package com.mclowicz.weatherstack.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.local.mapToWeather
import com.mclowicz.weatherstack.domain.repository.WeatherRepository
import com.mclowicz.weatherstack.presentation.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseVM() {

    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState> = _viewState

    fun searchQuery(query: String) {
        disposable.add(
            subscribe(
                request = weatherRepository.searchQuery(query)
                    .doOnSubscribe { _viewState.postValue(DetailViewState.Loading) },
                onSuccess = { weatherEntity ->
                    saveWeather(weatherEntity = weatherEntity)
                    _viewState.value = DetailViewState
                        .Content(data = mapToWeather(weatherEntity = weatherEntity))
                },
                onError = { throwable ->
                    _viewState.value =
                        DetailViewState.Error(message = throwable.localizedMessage)
                })
        )
    }

    private fun saveWeather(weatherEntity: WeatherEntity) {
        disposable.add(
            subscribe(
                request = weatherRepository.saveWeather(weatherEntity = weatherEntity)
            )
        )
    }
}