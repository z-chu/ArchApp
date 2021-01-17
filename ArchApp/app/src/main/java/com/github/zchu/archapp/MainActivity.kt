package com.github.zchu.archapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zchu.archapp.ui.dashboard.DashboardFragment
import com.github.zchu.archapp.ui.home.HomeFragment
import com.github.zchu.archapp.user.moduleservice.MineFragmentCreator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saltoken.commonbase.fragment.FragmentTabController
import com.saltoken.commonbase.fragment.setupWithFragmentTabController
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {


    private lateinit var fragmentTabController: FragmentTabController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        fragmentTabController = navView.setupWithFragmentTabController(
            R.id.fragment_container_view,
            supportFragmentManager,
            savedInstanceState,
            R.id.navigation_home
        ) {
            when (it) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_dashboard -> DashboardFragment()
                R.id.navigation_notifications -> {
                    getKoin().get<MineFragmentCreator>()
                        .createMineFragment("测试参数")
                }
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentTabController.saveInstanceState(outState)
    }
}