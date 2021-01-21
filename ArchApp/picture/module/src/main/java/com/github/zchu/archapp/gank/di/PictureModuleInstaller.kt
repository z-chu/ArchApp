package com.github.zchu.archapp.gank.di

import com.github.zchu.archapp.gank.data.UnsplashDataSource
import com.github.zchu.archapp.gank.data.UnsplashService
import com.github.zchu.archapp.gank.viewmodel.UnsplashViewModel
import com.google.auto.service.AutoService
import com.saltoken.common.remote.createOkHttpClient
import com.saltoken.common.remote.createWebService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import com.saltoken.commonbase.koin.isDebug
import com.saltoken.commonbase.network.HeaderInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class PictureModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {
        single {
            createWebService<UnsplashService>(
                createOkHttpClient(androidContext(), isDebug(), "UnsplashService") {
                    it.addInterceptor(
                        HeaderInterceptor(
                            mapOf(
                                "Authorization" to "Client-ID " + "b497ee359c1a7f23296704b32b54ef62f6f1c6c690452a54468ad70bb99a6510"
                            )
                        )
                    )
                },
                "https://api.unsplash.com/"
            )
        }


        single {
            UnsplashDataSource(get())
        }

        viewModel {
            UnsplashViewModel(get(), get())
        }


    }

}