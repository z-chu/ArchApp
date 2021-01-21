package com.github.zchu.archapp.gank

import androidx.fragment.app.Fragment
import com.github.zchu.archapp.gank.service.GankFragmentCreator
import com.github.zchu.archapp.moduleservice.mockkit.MockFragment
import com.google.auto.service.AutoService
import com.saltoken.commonbase.koin.KoinAutoInstallable
import com.saltoken.commonbase.koin.ModuleAutoInstallable
import org.koin.core.module.Module
import org.koin.dsl.module


@Suppress("unused")
@AutoService(KoinAutoInstallable::class)
class GankModuleInstaller : ModuleAutoInstallable() {

    override val module: Module = module {
        single<GankFragmentCreator> {
            object : GankFragmentCreator {
                override fun createFragment(): Fragment {
                    return MockFragment()
                }

            }
        }
    }

}