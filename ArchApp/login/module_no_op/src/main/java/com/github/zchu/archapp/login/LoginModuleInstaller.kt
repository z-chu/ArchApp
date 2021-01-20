package com.github.zchu.archapp.login

import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class LoginModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {
        module {
            single<SignInActivityStarter> {
                SignInActivityStarterImpl()
            }
        }
    }

}