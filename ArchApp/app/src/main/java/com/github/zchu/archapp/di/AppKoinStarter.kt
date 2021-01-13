package com.github.zchu.archapp.di

import android.content.Context
import com.github.zchu.archapp.BuildConfig
import com.github.zchu.archapp.login.di.loginModule
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object AppKoinStarter {

     private val modules = listOf(
         appModule,
         loginModule(BuildConfig.LEANCLOUD_SERVER_URL)
     )

    private val properties=mapOf(
        "appName" to "archApp" //示例
    )

    fun start(context: Context) {
        startKoin {
            isDebug(BuildConfig.DEBUG)
            androidContext(context)
            if(BuildConfig.DEBUG) {
                androidLogger()
            }
            modules(modules)
            properties(properties)
        }
    }
}