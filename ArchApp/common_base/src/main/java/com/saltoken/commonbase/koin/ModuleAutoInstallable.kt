package com.saltoken.commonbase.koin

import org.koin.core.module.Module

abstract class ModuleAutoInstallable : KoinAutoInstallable {

    override val modules: List<Module> by lazy(LazyThreadSafetyMode.NONE) { listOf(module) }

    abstract val module: Module

    final override val properties: Map<String, String>
        get() = emptyMap()

}