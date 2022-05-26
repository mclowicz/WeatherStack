package com.mclowicz.weatherstack.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mclowicz.weatherstack.data.local.WeatherEntity
import com.mclowicz.weatherstack.data.local.mapToWeather
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
            weatherRepository.searchQuery(query = query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _viewState.value = DetailViewState.Loading
                }
                .subscribe({
                    saveWeather(weatherEntity = it)
                    _viewState.value = DetailViewState
                        .Content(data = mapToWeather(weatherEntity = it))
                }, {
                    _viewState.value =
                        DetailViewState.Error(message = it.localizedMessage)
                })
        )
    }

    private fun saveWeather(weatherEntity: WeatherEntity) {
        disposable.add(
            weatherRepository.saveWeather(weatherEntity = weatherEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}