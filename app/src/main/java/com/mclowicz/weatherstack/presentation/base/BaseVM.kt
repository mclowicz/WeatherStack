package com.mclowicz.weatherstack.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseVM : ViewModel() {

    val disposable = CompositeDisposable()

    fun <T> subscribe(
        request: Single<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return request
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onSuccess.invoke(it) }, { onError.invoke(it) })

    }

    fun <T> subscribe(
        request: Observable<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return request
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onSuccess.invoke(it) }, { onError.invoke(it) })

    }

    fun subscribe(
        request: Completable
    ): Disposable {
        return request
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}