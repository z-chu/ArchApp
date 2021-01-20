package com.github.zchu.archapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.github.zchu.archapp.data.WelcomeService
import com.github.zchu.archapp.data.bean.Welcome
import com.github.zchu.common.rx.RxViewModel
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.rx.applySchedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit

class LauncherViewModel(
    private val welcomeService: WelcomeService,
    private val appSchedulers: AppSchedulers
) : RxViewModel() {

    val welcomes: MutableLiveData<List<Welcome>> = MutableLiveData()

    val countDown: MutableLiveData<Int> = MutableLiveData()

    private var disposable: Disposable? = null

    fun fetchWelcomes() {
        welcomeService
            .fetchWelcomes()
            .map {
                it.results
            }
            .applySchedulers(appSchedulers)
            .subscribeBy(onNext = {
                welcomes.value = it
            })
            .disposeWhenCleared()


    }

    fun startCountDown(second: Int) {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        val disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .map { increaseTime -> second - increaseTime.toInt() }
            .take((second + 1).toLong())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                countDown.value = it
            })
        disposable.disposeWhenCleared()
        this.disposable = disposable

    }

    fun stopCountDown() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }


}