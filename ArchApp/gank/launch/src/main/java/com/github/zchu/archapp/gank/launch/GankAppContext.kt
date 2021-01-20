package com.github.zchu.archapp.gank.launch

import com.saltoken.common.AppBaseContext

class GankAppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        GankKoinStarter.start(this)
    }

}