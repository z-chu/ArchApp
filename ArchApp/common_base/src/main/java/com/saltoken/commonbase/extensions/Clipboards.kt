package com.saltoken.commonbase.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyTextToClipboard(label: CharSequence, text: CharSequence): Boolean {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE)
    return if (clipboardManager is ClipboardManager) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))
        true
    } else {
        false
    }
}

fun CharSequence.toClipboard(context: Context, label: CharSequence): Boolean {
    return context.copyTextToClipboard(label, this)
}