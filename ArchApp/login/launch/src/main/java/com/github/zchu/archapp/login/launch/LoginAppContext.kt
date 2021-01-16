package com.github.zchu.archapp.login.launch

import com.github.zchu.archapp.usersession.UserSessionManager
import com.saltoken.common.AppBaseContext

class LoginAppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        LoginKoinStarter.start(this)
        UserSessionManager.initialize(this)
    }

}