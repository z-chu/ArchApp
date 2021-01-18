package com.github.zchu.archapp.user.di

import com.github.zchu.archapp.user.moduleservice.MineFragmentCreator
import com.github.zchu.archapp.user.moduleservice.MineFragmentCreatorImpl
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class UserModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {

        factory<MineFragmentCreator> {
            MineFragmentCreatorImpl()
        }

    }

}