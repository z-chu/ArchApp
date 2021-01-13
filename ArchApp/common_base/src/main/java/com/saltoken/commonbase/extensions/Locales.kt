package com.saltoken.commonbase.extensions

import android.content.Context
import android.os.Build
import com.saltoken.commonbase.commonBaseContext
import java.util.*


const val LANGUAGE_PREFIX_ZH = "zh-cn"
const val LANGUAGE_PREFIX_KO = "ko-kr"
const val LANGUAGE_PREFIX_EN = "en-us"
const val LANGUAGE_PREFIX_JA = "ja-jp"
const val LANGUAGE_PREFIX_RU = "ru-ru"
const val LANGUAGE_PREFIX_TR = "tr-tr"
const val LANGUAGE_PREFIX_ES = "es-es"
const val LANGUAGE_PREFIX_FR = "fr-fr"


fun getSysLocale(): Locale {
    return commonBaseContext.getLocale()
}

fun getSysLanguagePrefix(): String {
    return  getSysLocale().getLanguagePrefix()
}

fun Context.getLanguagePrefix(): String {
    return getLocale().getLanguagePrefix()
}

fun Context.getLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales[0]
    } else {
        resources.configuration.locale
    }
}


fun Locale.getLanguagePrefix(): String {
    val toLanguageTag = language
    return when {
        toLanguageTag.startsWith("zh") -> LANGUAGE_PREFIX_ZH
        toLanguageTag.startsWith("ko") -> LANGUAGE_PREFIX_KO
        toLanguageTag.startsWith("ja") -> LANGUAGE_PREFIX_JA
        toLanguageTag.startsWith("ru") -> LANGUAGE_PREFIX_RU
        toLanguageTag.startsWith("tr") -> LANGUAGE_PREFIX_TR
        toLanguageTag.startsWith("es") -> LANGUAGE_PREFIX_ES
        toLanguageTag.startsWith("fr") -> LANGUAGE_PREFIX_FR
        else -> LANGUAGE_PREFIX_EN
    }
}


fun Locale.isChinese(): Boolean {
    return language.startsWith("zh")
}

fun Locale.isKorean(): Boolean {
    return language.startsWith("ko")
}

fun Locale.isJapanese(): Boolean {
    return language.startsWith("ja")
}


fun Locale.isRussian(): Boolean {
    return language.startsWith("ru")
}

fun Locale.isTurkish(): Boolean {
    return language.startsWith("tr")
}


fun Locale.isSpanish(): Boolean {
    return language.startsWith("es")
}


fun Locale.isEnglish(): Boolean {
    return language.startsWith("en")
}

fun Locale.isFrench(): Boolean {
    return language.startsWith("fr")
}






