package com.saltoken.commonbase.koin

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap

fun KoinApplication.installAutoRegister(isShowLog: Boolean = true) {
    val installableServiceLoader = ServiceLoader.load(KoinAutoInstallable::class.java)
    val modules = ArrayList<Module>(20)
    val properties = HashMap<String, String>()
    var installables: ArrayList<KoinAutoInstallable>? = null
    if (isShowLog) {
        installables = ArrayList<KoinAutoInstallable>(20)
    }

    installableServiceLoader.iterator().forEach {
        installables?.add(it)
        modules.addAll(it.modules)
        for (property in it.properties) {
            val value = properties[property.key]
            if (value != null) {
                Timber.e(
                    "出现key相同的property，请修改key防止冲突。key=${property.key},value1=${value}]，value2=${value}]。"
                )
            }
            properties[property.key] = property.value
        }
    }
    if (installables != null) {
        Timber.d("Auto install :${installables.map { it.javaClass.name }}")
    }
    modules(modules)
    properties(properties)
}