package com.github.zchu.archapp.moduleservice.mockkit

import android.content.Context
import android.content.Intent

fun mockActivityIntent(context: Context): Intent {
    return Intent(context, MockActivity::class.java)
}

fun mockServiceIntent(context: Context): Intent {
    TODO("这里的代码还没开始写")
}
