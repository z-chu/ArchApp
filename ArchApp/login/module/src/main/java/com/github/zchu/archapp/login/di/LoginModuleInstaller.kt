package com.github.zchu.archapp.login.di

import com.github.zchu.archapp.login.SignInActivity
import com.github.zchu.archapp.login.data.LoginDataSource
import com.github.zchu.archapp.login.data.LoginRepository
import com.github.zchu.archapp.login.data.LoginService
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.login.usecase.SaveSessionUseCase
import com.github.zchu.archapp.login.viewmodel.LoginViewModel
import com.google.auto.service.AutoService
import com.saltoken.common.koin.LeanCloudConfig
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
class LoginModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {
        single {
            val leanCloudConfig = get<LeanCloudConfig>()
            createWebService<LoginService>(
                createOkHttpClient(androidContext(), isDebug(), "LoginService") {
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

        single {
            LoginDataSource(get())
        }

        single {
            LoginRepository(get())
        }



        scope<SignInActivity> { //限定符，只允许在SignInActivity中进行注入 ,防止其他人用我的代码
            viewModel {
                LoginViewModel(get(), get(), get())
            }
        }

        factory {
            SaveSessionUseCase(get())
        }

        factory<SignInActivityStarter> {
            SignInActivity.Companion
        }


    }

}