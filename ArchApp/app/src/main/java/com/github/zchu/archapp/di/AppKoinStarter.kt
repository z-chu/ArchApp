package com.github.zchu.archapp.di

import android.content.Context
import com.github.zchu.archapp.BuildConfig
import com.saltoken.common.koin.LeanCloudConfig
import com.saltoken.common.koin.leanCloud
import com.saltoken.commonbase.koin.installAutoRegister
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object AppKoinStarter {


    private val properties = mapOf(
        "appName" to "archApp" //示例
    )

    fun start(context: Context) {
        startKoin {
            isDebug(BuildConfig.DEBUG)
            leanCloud(
                LeanCloudConfig(
                    BuildConfig.LEANCLOUD_SERVER_URL,
                    BuildConfig.LEANCLOUD_APP_ID,
                    BuildConfig.LEANCLOUD_APP_KEY
                )
            )
            androidContext(context)
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            properties(properties)
            modules(appModule)
            installAutoRegister()

        }
    }

}