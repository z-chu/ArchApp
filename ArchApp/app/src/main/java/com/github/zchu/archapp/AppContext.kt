package com.github.zchu.archapp

import com.github.zchu.archapp.di.AppKoinStarter
import com.saltoken.common.AppBaseContext
import com.saltoken.commonbase.CommonBase

class AppContext : AppBaseContext() {

    override fun onCreate() {
        super.onCreate()
        AppKoinStarter.start(this)
    }



    companion object {
        val context: AppContext
            get() = AppBaseContext.context as AppContext
    }
}