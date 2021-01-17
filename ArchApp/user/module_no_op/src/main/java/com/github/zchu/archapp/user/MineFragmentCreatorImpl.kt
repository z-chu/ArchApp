package com.github.zchu.archapp.user

import androidx.fragment.app.Fragment
import com.github.zchu.archapp.moduleservice.mockkit.MockFragment
import com.github.zchu.archapp.user.moduleservice.MineFragmentCreator

class MineFragmentCreatorImpl : MineFragmentCreator {

    override fun createMineFragment(parameter: String): Fragment {
        return MockFragment()
    }

}
