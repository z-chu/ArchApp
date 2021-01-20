package com.github.zchu.archapp.gank.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.zchu.archapp.gank.GankGroupFragment
import com.saltoken.common.base.FragmentContainerActivity

class GankLaunchActivity : FragmentContainerActivity() {

    override fun onCreated(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gank_launch)
    }

    override fun createFragment(): Fragment {
        return GankGroupFragment()
    }
}