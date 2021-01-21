package com.github.zchu.archapp

import com.github.zchu.archapp.di.AppKoinStarter
import com.github.zchu.archapp.usersession.UserSessionManager
import com.saltoken.common.AppBaseContext
import timber.log.Timber
import timber.log.Timber.DebugTree


class AppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        AppKoinStarter.start(this)
        UserSessionManager.initialize(this)
    }



    companion object {
        val context: AppContext
            get() = AppBaseContext.context as AppContext
    }
}