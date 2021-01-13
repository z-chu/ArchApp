package com.saltoken.common

import android.content.Context
import androidx.annotation.CallSuper
import com.saltoken.commonbase.CommonBase

open class AppBaseContext : BaseFeatureApplication() {

    @CallSuper
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = this
    }

    override fun onCreate() {
        super.onCreate()
        CommonBase.init(this)
    }



    companion object {
        lateinit var context: AppBaseContext
            private set
    }
}