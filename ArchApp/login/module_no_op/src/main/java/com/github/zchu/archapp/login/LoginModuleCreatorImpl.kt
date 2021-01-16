package com.github.zchu.archapp.login

import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.github.zchu.archapp.login.service.SignInActivityStarter
import org.koin.dsl.module

class LoginModuleCreatorImpl : LoginModuleCreator {
    override fun loginModule(url: String, leanCloudAppId: String, leanCloudAppKey: String) =
        module {
            single<SignInActivityStarter> {
                SignInActivityStarterImpl()
            }
        }

}