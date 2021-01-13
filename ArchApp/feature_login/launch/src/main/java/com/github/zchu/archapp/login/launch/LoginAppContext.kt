package com.github.zchu.archapp.login.launch

import com.saltoken.common.AppBaseContext

class LoginAppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        LoginKoinStarter.start(this)
    }

}