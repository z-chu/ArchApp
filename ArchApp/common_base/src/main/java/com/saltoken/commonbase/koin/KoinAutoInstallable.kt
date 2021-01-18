package com.saltoken.commonbase.koin

import org.koin.core.module.Module

@Suppress("unused")
interface KoinAutoInstallable {

    val modules: List<Module>

    val properties: Map<String, String>

}