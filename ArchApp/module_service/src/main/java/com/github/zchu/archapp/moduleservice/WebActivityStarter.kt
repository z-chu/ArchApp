package com.github.zchu.archapp.moduleservice

import android.content.Context
import android.content.Intent

interface WebActivityStarter {

    fun newIntent(context: Context, url: String, title: String? = null): Intent

    fun start(context: Context, url: String, title: String? = null)

}