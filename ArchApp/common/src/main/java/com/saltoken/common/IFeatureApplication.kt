package com.saltoken.common

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration

interface IFeatureApplication {

    fun attachBaseContext(base: Context)

    fun onCreate()

    fun onConfigurationChanged(newConfig: Configuration)

    fun onLowMemory()

    fun onTrimMemory(level: Int)

    fun registerComponentCallbacks(callback: ComponentCallbacks)

    fun unregisterComponentCallbacks(callback: ComponentCallbacks)

    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks)

    fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks)

    fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener)

    fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener)
}