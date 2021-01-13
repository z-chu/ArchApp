package com.saltoken.commonbase.extensions

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

fun Context.isNightMode(): Boolean {
    return resources
        .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun isNightMode(nightMode: Int, isDefaultNightMode: Boolean): Boolean {
    return when (nightMode) {
        AppCompatDelegate.MODE_NIGHT_NO -> {
            false
        }
        AppCompatDelegate.MODE_NIGHT_YES -> {
            true
        }
        AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> {
            if (!isDefaultNightMode) {
                return isNightMode(
                    AppCompatDelegate.getDefaultNightMode(),
                    true
                )
            } else {
                false
            }
        }
        else -> {
            false
        }
    }
}

fun AppCompatActivity.isNightMode(): Boolean {
    return isNightMode(delegate.localNightMode, false)
}