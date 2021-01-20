package com.saltoken.common.koin

import org.koin.android.error.MissingAndroidContextException
import org.koin.core.KoinApplication
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module


fun KoinApplication.leanCloud(
    leanCloudConfig: LeanCloudConfig,
    name: String? = null
): KoinApplication {
    koin.loadModules(listOf(module {
        if (name != null) {
            single(named(name)) { leanCloudConfig }
        } else {
            single { leanCloudConfig }
        }
    }))

    return this;
}

const val ERROR_MSG = "Please use leanCloud() function in your KoinApplication configuration."

/**
 * DSL extension - Resolve LeanCloudConfig
 *
 * @author zchu
 */
fun Scope.leanCloud(name: String? = null): LeanCloudConfig = try {
    if (name != null) {
        get(named(name))
    } else {
        get()
    }
} catch (e: Exception) {
    if (name != null) {
        throw MissingAndroidContextException("Can't resolve LeanCloudConfig. It name is $name . $ERROR_MSG")
    } else {
        throw MissingAndroidContextException("Can't resolve LeanCloudConfig. $ERROR_MSG")
    }
}

