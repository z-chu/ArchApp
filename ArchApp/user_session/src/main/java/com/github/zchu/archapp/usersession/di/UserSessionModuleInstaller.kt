package com.github.zchu.archapp.usersession.di

import com.github.zchu.archapp.usersession.UserSessionManager
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class UserSessionModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {

        single { UserSessionManager(androidContext()) }

    }

}