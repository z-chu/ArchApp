package com.github.zchu.archapp.login.launch

import android.content.Context
import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.koin.KoinModuleProvider
import com.saltoken.commonbase.koin.isDebug
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

object LoginKoinStarter {

    private val modules = listOf(
        module {
            single { AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }
        }
    )

    private val properties = mapOf(
        "appName" to "archApp" //示例
    )

    fun start(context: Context) {
        startKoin {
            isDebug(true)
            androidContext(context)
            modules(modules)
            modules(getAutoRegisterModules())
            modules(
                koin.get<LoginModuleCreator>().loginModule(
                    "https://fn78orw2.api.lncld.net/1.1/",
                    "fn78orw2hVO4V4d5n8VBypj7-gzGzoHsz",
                    "eGNJCnxUVelIBTo14A1SQkdr"
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