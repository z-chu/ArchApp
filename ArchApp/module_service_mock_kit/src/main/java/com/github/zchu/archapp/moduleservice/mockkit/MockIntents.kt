package com.github.zchu.archapp.moduleservice.mockkit

import android.content.Context
import android.content.Intent

fun mockActivityIntent(context: Context, message: String? = null): Intent {
    return MockActivity.newIntent(context, message)
}

fun mockServiceIntent(context: Context): Intent {
    TODO("这里的代码还没开始写")
}
