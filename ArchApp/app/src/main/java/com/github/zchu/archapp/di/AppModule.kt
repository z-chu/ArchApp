package com.github.zchu.archapp.di

import com.github.zchu.archapp.LauncherActivity
import com.github.zchu.archapp.MainActivity
import com.github.zchu.archapp.data.WelcomeService
import com.github.zchu.archapp.moduleservice.MainActivityStarter
import com.github.zchu.archapp.viewmodel.LauncherViewModel
import com.saltoken.common.koin.LeanCloudConfig
import com.saltoken.common.remote.createOkHttpClient
import com.saltoken.common.remote.createWebService
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.koin.isDebug
import com.saltoken.commonbase.network.HeaderInterceptor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    /**
     * 全局线程控制
     */
    single { AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }

    factory<MainActivityStarter> { MainActivity.Companion }

    single<WelcomeService> {
        val leanCloudConfig = get<LeanCloudConfig>()
        createWebService(
            createOkHttpClient(androidContext(), isDebug(), "WelcomeService") {
                it.addInterceptor(
                    HeaderInterceptor(
                        mapOf(
                            "X-LC-Id" to leanCloudConfig.appId,
                            "X-LC-Key" to leanCloudConfig.appKey,
                        )
                    )
                )
            },
            leanCloudConfig.serverUrl
        )
    }

    scope<LauncherActivity> {//限定符，只允许在 LauncherActivity 中进行注入 ,防止其他人用我的代码
        viewModel { LauncherViewModel(get(), get()) }
    }


}

