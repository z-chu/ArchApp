package com.github.zchu.archapp.login.di


import com.github.zchu.archapp.login.SignInActivity
import com.github.zchu.archapp.login.data.LoginService
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.login.viewmodel.LoginViewModel
import com.github.zchu.archapp.remotebase.di.createOkHttpClient
import com.github.zchu.archapp.remotebase.di.createWebService
import com.github.zchu.archapp.remotebase.di.remoteModule
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

fun loginModule(url: String) = remoteModule(url) { serverUrl ->

    single {
        createWebService<LoginService>(
            createOkHttpClient(androidContext(), isDebug(), "LoginService"),
            serverUrl
        )
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    factory<SignInActivityStarter> {
        SignInActivity.Companion
    }

}