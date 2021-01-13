package com.github.zchu.archapp.login.service

import android.content.Context
import android.content.Intent

interface SignInActivityStarter {

    fun newIntent(context: Context, pendingIntent: Intent? = null): Intent


    fun start(context: Context, pendingIntent: Intent? = null)


}