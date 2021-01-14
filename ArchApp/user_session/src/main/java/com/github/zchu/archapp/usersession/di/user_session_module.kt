package com.github.zchu.archapp.usersession.di

import com.github.zchu.archapp.usersession.UserSessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userSessionModule = module {
    single { UserSessionManager(androidContext()) }
    //single { UserKycPreferences(androidContext()) }
    //  single { UserSecurityPreferences(androidContext()) }
}