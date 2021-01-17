package com.github.zchu.archapp.di

import android.content.Context
import com.github.zchu.archapp.BuildConfig
import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.saltoken.commonbase.koin.KoinModuleProvider
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

object AppKoinStarter {

    private val modules = listOf(
        appModule
    )

    private val properties = mapOf(
        "appName" to "archApp" //示例
    )

    fun start(context: Context) {
        startKoin {
            isDebug(BuildConfig.DEBUG)
            androidContext(context)
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            modules(modules)
            modules(getAutoRegisterModules())
            modules(
                koin.get<LoginModuleCreator>().loginModule(
                    BuildConfig.LEANCLOUD_SERVER_URL,
                    BuildConfig.LEANCLOUD_APP_ID,
                    BuildConfig.LEANCLOUD_APP_KEY
                )
            )

            properties(properties)
        }
    }

    private fun getAutoRegisterModules(): List<Module> {
        val moduleProviders = ServiceLoader.load(KoinModuleProvider::class.java)
        val modules = ArrayList<Module>()
        moduleProviders.iterator().forEach {
            modules.addAll(it.modules())
        }
        Timber.d("AutoRegisterModules:$modules")
        return modules
    }
}