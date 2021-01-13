package com.saltoken.commonbase.networkstatus

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.pwittchen.reactivenetwork.library.rx3.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx3.ReactiveNetwork

private val _liveNetworkStatus = MutableLiveData<Connectivity>()

val liveNetworkStatus: LiveData<Connectivity>
    get() = _liveNetworkStatus

@SuppressLint("CheckResult")
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
internal fun setUpLiveNetwork(context: Context) {
    val applicationContext = context.applicationContext
    ReactiveNetwork
        .observeNetworkConnectivity(applicationContext)
        .subscribe { connectivity ->
            if (Looper.myLooper() == Looper.getMainLooper()) {
                _liveNetworkStatus.value = connectivity
            } else {
                _liveNetworkStatus.postValue(connectivity)
            }
        }
}