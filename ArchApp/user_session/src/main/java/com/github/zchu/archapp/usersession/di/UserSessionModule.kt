package com.github.zchu.archapp.usersession.di

import com.github.zchu.archapp.usersession.UserSessionManager
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinModuleProvider
import com.saltoken.commonbase.koin.SingleKoinModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.ModuleDeclaration

@AutoService(KoinModuleProvider::class)
class UserSessionModule : SingleKoinModuleProvider() {
    override fun module(): ModuleDeclaration = {
        single { UserSessionManager(androidContext()) }
    }

}