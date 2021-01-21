package com.saltoken.common

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import com.github.zchu.common.help.ToastDef
import com.saltoken.commonbase.CommonBase

open class AppBaseContext : Application() {

    @CallSuper
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = this
    }

    override fun onCreate() {
        super.onCreate()
        CommonBase.init(this)
        ActivityStacker.init(this)
        ToastDef.defaultContext = this
    }



    companion object {
        lateinit var context: AppBaseContext
            private set
    }
}