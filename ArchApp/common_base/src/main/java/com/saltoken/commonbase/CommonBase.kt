package com.saltoken.commonbase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission

internal val commonBaseContext: Context
    get() = CommonBase.commonBaseContext

fun initCommonBase(context: Context) {
    CommonBase.init(context)
}

object CommonBase {

    internal lateinit var commonBaseContext: Context

    @JvmStatic
    fun init(context: Context) {
        commonBaseContext = context.applicationContext
    }

    @JvmStatic
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun setUpLiveNetwork(context: Context) {
        if(!this::commonBaseContext.isInitialized){
            init(context)
        }
        com.saltoken.commonbase.networkstatus.setUpLiveNetwork(context)
    }

}