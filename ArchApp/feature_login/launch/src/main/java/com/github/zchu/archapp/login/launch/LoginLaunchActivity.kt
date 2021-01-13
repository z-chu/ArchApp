package com.github.zchu.archapp.login.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.saltoken.commonbase.koin.isDebug
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LoginLaunchActivity : AppCompatActivity() {

    private val signInActivityStarter:SignInActivityStarter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_launch)
        signInActivityStarter.start(this)
        finish()

    }
}