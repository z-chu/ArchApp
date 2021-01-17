package com.github.zchu.archapp.login

import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinModuleProvider
import org.koin.core.module.Module
import org.koin.dsl.module

@AutoService(KoinModuleProvider::class)
class LoginModuleProvider : KoinModuleProvider {

    override fun modules(): List<Module> {
        return listOf(
            module {
                factory<LoginModuleCreator> {
                    LoginModuleCreatorImpl()
                }
            }
        )
    }

}