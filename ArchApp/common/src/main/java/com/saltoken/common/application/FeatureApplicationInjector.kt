package com.saltoken.common.application

import android.content.Context
import android.content.res.Configuration


object FeatureApplicationInjector {

    private var featureApplications: Array<IFeatureApplication> = emptyArray()

    fun startInjector(featureApplicationClasses: Set<Class<out IFeatureApplication>>) {
        val arrayOfNulls = arrayOfNulls<IFeatureApplication>(featureApplicationClasses.size)
        featureApplicationClasses.forEachIndexed { index, clazz ->
            val newInstance = clazz.newInstance()
            arrayOfNulls[index] = newInstance
        }
        @Suppress("UNCHECKED_CAST")
        featureApplications = arrayOfNulls as Array<IFeatureApplication>
    }

    fun attachBaseContext(base: Context) {
        for (featureApplication in featureApplications) {
            featureApplication.attachBaseContext(base)
        }
    }

    fun onCreate() {
        for (featureApplication in featureApplications) {
            featureApplication.onCreate()
        }
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        for (featureApplication in featureApplications) {
            featureApplication.onConfigurationChanged(newConfig)
        }
    }

    fun onLowMemory() {
        for (featureApplication in featureApplications) {
            featureApplication.onLowMemory()
        }
    }

    fun onTrimMemory(level: Int) {
        for (featureApplication in featureApplications) {
            featureApplication.onTrimMemory(level)
        }
    }

}