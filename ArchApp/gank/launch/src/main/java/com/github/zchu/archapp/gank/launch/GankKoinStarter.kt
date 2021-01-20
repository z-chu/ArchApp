package com.github.zchu.archapp.gank.launch

import android.content.Context
import android.content.Intent
import com.github.zchu.archapp.moduleservice.WebActivityStarter
import com.github.zchu.archapp.moduleservice.mockkit.mockActivityIntent
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.koin.installAutoRegister
import com.saltoken.commonbase.koin.isDebug
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

object GankKoinStarter {

    private val modules = listOf(
        module {
            single { AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }

            single<WebActivityStarter> {
                object : WebActivityStarter {
                    override fun newIntent(context: Context, url: String, title: String?): Intent {
                        return mockActivityIntent(context, "WebActivityStarter")
                    }

                    override fun start(context: Context, url: String, title: String?) {
                        context.startActivity(mockActivityIntent(context, "WebActivityStarter"))

                    }

                }
            }
        }
    )


    fun start(context: Context) {
        startKoin {
            isDebug(true)
            androidContext(context)
            modules(modules)
            installAutoRegister()
        }
    }


}