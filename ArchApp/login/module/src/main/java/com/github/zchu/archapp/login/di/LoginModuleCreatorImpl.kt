package com.github.zchu.archapp.login.di

import com.github.zchu.archapp.login.SignInActivity
import com.github.zchu.archapp.login.data.LoginDataSource
import com.github.zchu.archapp.login.data.LoginRepository
import com.github.zchu.archapp.login.data.LoginService
import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.login.usecase.SaveSessionUseCase
import com.github.zchu.archapp.login.viewmodel.LoginViewModel
import com.saltoken.common.remote.createOkHttpClient
import com.saltoken.common.remote.createWebService
import com.saltoken.commonbase.koin.isDebug
import com.saltoken.commonbase.network.HeaderInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal class LoginModuleCreatorImpl : LoginModuleCreator {

    override fun loginModule(url: String, leanCloudAppId: String, leanCloudAppKey: String) =
        module {

            single {
                createWebService<LoginService>(
                    createOkHttpClient(androidContext(), isDebug(), "LoginService") {
                        it.addInterceptor(
                            HeaderInterceptor(
                                mapOf(
                                    "X-LC-Id" to leanCloudAppId,
                                    "X-LC-Key" to leanCloudAppKey,
                                )
                            )
                        )
                    },
                    url
                )
            }

            single {
                LoginDataSource(get())
            }

            single {
                LoginRepository(get())
            }

            viewModel {
                LoginViewModel(get(), get(), get())
            }

            factory {
                SaveSessionUseCase(get())
            }

            factory<SignInActivityStarter> {
                SignInActivity.Companion
            }

        }
}