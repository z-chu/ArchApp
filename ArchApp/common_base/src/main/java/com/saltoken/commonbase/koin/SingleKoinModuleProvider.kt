package com.saltoken.commonbase.koin

import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration

abstract class SingleKoinModuleProvider(
    private val createdAtStart: Boolean = false,
    private val override: Boolean = false
) : KoinModuleProvider {

    override fun modules(): List<Module> {
        val module = Module(createdAtStart, override)
        module().invoke(module)
        return listOf(module)
    }

    abstract fun module(): ModuleDeclaration

}