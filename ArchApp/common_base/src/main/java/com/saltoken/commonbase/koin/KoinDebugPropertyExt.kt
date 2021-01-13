package com.saltoken.commonbase.koin

import org.koin.android.error.MissingAndroidContextException
import org.koin.core.KoinApplication
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun KoinApplication.isDebug(isDebug: Boolean): KoinApplication {
    koin.loadModules(listOf(module {
        factory(named("KOIN_IS_DEBUG")) { isDebug }
    }))

    return this;
}

const val ERROR_MSG = "Please use isDebug() function in your KoinApplication configuration."

/**
 * DSL extension - Resolve isDebug
 *
 * @author zchu
 */
fun Scope.isDebug(): Boolean = try {
    get(named("KOIN_IS_DEBUG"))
} catch (e: Exception) {
    throw MissingAndroidContextException("Can't resolve isDebug property. $ERROR_MSG")
}

