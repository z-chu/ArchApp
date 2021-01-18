package com.saltoken.commonbase.koin

import org.koin.core.module.Module

abstract class ModulesAutoInstallable : KoinAutoInstallable {

    abstract override val modules: List<Module>

    final override val properties: Map<String, String>
        get() = emptyMap()

}