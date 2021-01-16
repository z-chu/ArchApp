package com.github.zchu.archapp.login.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zchu.archapp.login.service.SignInActivityStarter
import org.koin.android.ext.android.inject

class LoginLaunchActivity : AppCompatActivity() {

    private val signInActivityStarter:SignInActivityStarter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_launch)
        signInActivityStarter.start(this)
        finish()

    }
}