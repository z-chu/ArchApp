package com.saltoken.common.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saltoken.common.R

abstract class FragmentContainerActivity : BaseActivity() {

    open val containerViewId: Int
        get() = R.id.container



    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreated(savedInstanceState)
        val supportFragmentManager = supportFragmentManager
        if (savedInstanceState == null || supportFragmentManager.findFragmentByTag("container_fragment") == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(containerViewId, createFragment(), "container_fragment")
                .commit()
        }
    }

    open fun onCreated(savedInstanceState: Bundle?) {
        setContentView(R.layout.abc_activity_fragment_container)
    }


    abstract fun createFragment(): Fragment

}