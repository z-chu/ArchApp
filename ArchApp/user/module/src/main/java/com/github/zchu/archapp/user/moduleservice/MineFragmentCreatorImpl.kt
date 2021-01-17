package com.github.zchu.archapp.user.moduleservice

import androidx.fragment.app.Fragment
import com.github.zchu.archapp.user.MineFragment

class MineFragmentCreatorImpl : MineFragmentCreator {

    override fun createMineFragment(parameter: String): Fragment {
        return MineFragment()
    }

}
