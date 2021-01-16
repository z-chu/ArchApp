package com.saltoken.common.koin

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun modules(): List<Module>
}