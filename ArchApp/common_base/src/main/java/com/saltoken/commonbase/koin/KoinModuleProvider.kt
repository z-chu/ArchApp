package com.saltoken.commonbase.koin

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun modules(): List<Module>
}