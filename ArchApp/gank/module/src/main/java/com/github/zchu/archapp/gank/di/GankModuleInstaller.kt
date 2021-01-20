package com.github.zchu.archapp.gank.di

import com.github.zchu.archapp.gank.data.GankDataSource
import com.github.zchu.archapp.gank.data.GankService
import com.github.zchu.archapp.gank.viewmodel.GankCategoryViewModel
import com.google.auto.service.AutoService
import com.saltoken.common.remote.createOkHttpClient
import com.saltoken.common.remote.createWebService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class GankModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {
        single {
            createWebService<GankService>(
                createOkHttpClient(androidContext(), isDebug(), "GankService"),
                "http://gank.io/api/"
            )
        }


        single {
            GankDataSource(get())
        }

        viewModel { (name: String) ->
            GankCategoryViewModel(name, get(), get())
        }

    }

}