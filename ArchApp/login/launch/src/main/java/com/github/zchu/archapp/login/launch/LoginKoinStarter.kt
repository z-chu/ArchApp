package com.github.zchu.archapp.login.launch

import android.content.Context
import com.github.zchu.archapp.moduleservice.MainActivityStarter
import com.github.zchu.archapp.moduleservice.mockkit.mockActivityIntent
import com.saltoken.common.koin.LeanCloudConfig
import com.saltoken.common.koin.leanCloud
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.koin.installAutoRegister
import com.saltoken.commonbase.koin.isDebug
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

object LoginKoinStarter {

    private val modules = listOf(
        module {
            single { AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }

            single<MainActivityStarter> {
                object : MainActivityStarter {
                    override fun start(context: Context) {
                        context.startActivity(mockActivityIntent(context, "MainActivityStarter"))
                    }

                }
            }
        }
    )

    private val properties = mapOf(
        "appName" to "archApp" //示例
    )

    fun start(context: Context) {
        startKoin {
            isDebug(true)
            leanCloud(
                LeanCloudConfig(
                    "https://fn78orw2.api.lncld.net/1.1/",
                    "fn78orw2hVO4V4d5n8VBypj7-gzGzoHsz",
                    "eGNJCnxUVelIBTo14A1SQkdr"
                )
            )
            androidContext(context)
            modules(modules)
            installAutoRegister()
            properties(properties)
        }
    }


}