package com.github.zchu.archapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.github.zchu.archapp.login.service.LoginModuleCreator
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        val loginModuleCreators = ServiceLoader.load(LoginModuleCreator::class.java)
        loginModuleCreators.forEach {
            Timber.d(it.toString())
        }
    }
}