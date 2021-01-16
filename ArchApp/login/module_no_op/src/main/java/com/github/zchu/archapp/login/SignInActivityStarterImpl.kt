package com.github.zchu.archapp.login

import android.content.Context
import android.content.Intent
import com.github.zchu.archapp.login.service.SignInActivityStarter

class SignInActivityStarterImpl : SignInActivityStarter {

    override fun newIntent(context: Context, pendingActivityIntent: Intent?): Intent {
      throw RuntimeException()
    }

    override fun start(context: Context, pendingActivityIntent: Intent?) {

    }

    override fun start(context: Context, callback: () -> Unit) {
    }

}