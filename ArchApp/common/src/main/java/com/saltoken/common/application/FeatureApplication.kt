package com.saltoken.common.application

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.annotation.CallSuper

open class FeatureApplication : ContextWrapper(null), IFeatureApplication {

    private val application: Application by lazy {
        val baseContext = baseContext
        if (baseContext is Application) {
            return@lazy baseContext
        }
        val applicationContext = applicationContext
        if (applicationContext != null) {
            return@lazy applicationContext as Application
        }
        throw  IllegalStateException("Application has not been completely created. Please wait for `onCreate()` to be called.")
    }

    @CallSuper
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
    }

    override fun onLowMemory() {
    }

    override fun onTrimMemory(level: Int) {
    }

    override fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.unregisterActivityLifecycleCallbacks(callback)
    }

    override fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        application.registerOnProvideAssistDataListener(callback)
    }

    override fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        application.unregisterOnProvideAssistDataListener(callback)
    }


}