package com.mclowicz.weatherstack.presentation.detail

sealed class CustomException() : Throwable() {
    class NotFoundException(override var message: String = "City not found."): CustomException()
    class NoInternetException(override var message: String = "Please, check your internet connection and try again."): CustomException()
}