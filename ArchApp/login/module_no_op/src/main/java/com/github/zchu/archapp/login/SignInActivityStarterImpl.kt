package com.github.zchu.archapp.login

import android.content.Context
import android.content.Intent
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.common.help.showToastShort

class SignInActivityStarterImpl : SignInActivityStarter {

    override fun newIntent(context: Context, pendingActivityIntent: Intent?): Intent {
      throw RuntimeException()
    }

    override fun start(context: Context, pendingActivityIntent: Intent?) {
        context.showToastShort("该组件未依赖")
    }

    override fun start(context: Context, callback: () -> Unit) {
        context.showToastShort("该组件未依赖")
    }

}