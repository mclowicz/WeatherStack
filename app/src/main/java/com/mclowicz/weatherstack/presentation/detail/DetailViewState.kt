package com.mclowicz.weatherstack.presentation.detail

import com.mclowicz.weatherstack.domain.model.Weather

sealed class DetailViewState() {
    object Loading : DetailViewState()
    class Error(val message: String?) : DetailViewState()
    class Content(val data: Weather) : DetailViewState()
}