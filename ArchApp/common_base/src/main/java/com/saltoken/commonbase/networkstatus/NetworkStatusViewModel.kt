package com.saltoken.commonbase.networkstatus

import android.net.NetworkInfo
import androidx.lifecycle.Observer
import com.github.pwittchen.reactivenetwork.library.rx3.Connectivity
import com.github.zchu.common.livedata.safeSetValue
import com.github.zchu.common.rx.RxViewModel
import com.github.zchu.model.*
import java.net.UnknownHostException

abstract class NetworkStatusViewModel<T> : RxViewModel() {

    protected val _result = MutableLiveResult<T>()

    val result: LiveResult<T>
        get() = _result

    private var cacheValue: T? = null

    private val cacheValueObserve = Observer<WorkResult<T>> {
        if (it is Success) {
            cacheValue = it.value
        }
    }

    init {
        _result.observeForever(cacheValueObserve)
    }

    fun getSuccessIncludeCache(): T? {
        return cacheValue
    }

    fun cleanCacheValue() {
        cacheValue = null
    }


    private val networkObserver = object : Observer<Connectivity> {
        override fun onChanged(it: Connectivity?) {
            if (it != null && it.state() == NetworkInfo.State.CONNECTED) {
                doTask(_result)
                liveNetworkStatus.removeObserver(this)
            } else {
                stop()
                _result.safeSetValue(Failure(UnknownHostException()))
            }
        }
    }

    operator fun invoke() {
        liveNetworkStatus.removeObserver(networkObserver)
        liveNetworkStatus
            .observeForever(networkObserver)
    }

    abstract fun doTask(mutableLiveResult: MutableLiveResult<T>)


    open fun stop() {

    }


    override fun onCleared() {
        super.onCleared()
        liveNetworkStatus.removeObserver(networkObserver)
        _result.removeObserver(cacheValueObserve)
    }
}