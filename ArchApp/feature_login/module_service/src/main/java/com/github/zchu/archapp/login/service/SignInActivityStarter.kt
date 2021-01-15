package com.github.zchu.archapp.login.service

import android.content.Context
import android.content.Intent
import androidx.annotation.MainThread

interface SignInActivityStarter {

    fun newIntent(context: Context, pendingActivityIntent: Intent? = null): Intent

    fun start(context: Context, pendingActivityIntent: Intent? = null)

    /**
     * @param callback 登录成功后回调该函数，调用该方法后会被内部以弱引用的方式被静态对象持有，外部应在接收到回调之前保留对它的强引用。
     */
    @MainThread
    fun start(context: Context, callback: () -> Unit)


}