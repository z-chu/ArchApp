package com.github.zchu.archapp.login.service

import org.koin.core.module.Module

interface LoginModuleCreator {
    fun loginModule(url: String, leanCloudAppId: String, leanCloudAppKey: String): Module
}