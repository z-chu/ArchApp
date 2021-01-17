package com.github.zchu.archapp.user

import com.github.zchu.archapp.user.moduleservice.MineFragmentCreator
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinModuleProvider
import com.saltoken.commonbase.koin.SingleKoinModuleProvider
import org.koin.dsl.ModuleDeclaration

@Suppress("unused")
@AutoService(KoinModuleProvider::class)
class UserModule : SingleKoinModuleProvider() {
    override fun module(): ModuleDeclaration = {
        factory<MineFragmentCreator> {
            MineFragmentCreatorImpl()
        }
    }

}