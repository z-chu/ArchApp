package com.saltoken.commonbase.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

fun SpannableString.highlight(str: String, @ColorInt color: Int): SpannableString {
    val indexOf = indexOf(str)
    check(indexOf >= 0)
    setSpan(ForegroundColorSpan(color), indexOf, indexOf + str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun CharSequence.highlight(str: String, @ColorInt color: Int): CharSequence {
    return SpannableString(this).highlight(str, color)
}