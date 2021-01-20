package com.saltoken.common.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.CallSuper

open class BaseFeatureApplication : Application() {

    @CallSuper
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        FeatureApplicationInjector.startInjector(featureApplications())
        FeatureApplicationInjector.attachBaseContext(this)
    }


    @CallSuper
    override fun onCreate() {
        super.onCreate()
        FeatureApplicationInjector.onCreate()
    }

    @CallSuper
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        FeatureApplicationInjector.onConfigurationChanged(newConfig)
    }

    @CallSuper
    override fun onLowMemory() {
        super.onLowMemory()
        FeatureApplicationInjector.onLowMemory()
    }

    @CallSuper
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        FeatureApplicationInjector.onTrimMemory(level)

    }

    open fun featureApplications(): Set<Class<out IFeatureApplication>> = emptySet()

}