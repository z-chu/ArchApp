package com.github.zchu.archapp

import com.github.zchu.archapp.di.AppKoinStarter
import com.github.zchu.archapp.usersession.UserSessionManager
import com.saltoken.common.AppBaseContext

class AppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        AppKoinStarter.start(this)
        UserSessionManager.initialize(this)
    }



    companion object {
        val context: AppContext
            get() = AppBaseContext.context as AppContext
    }
}