package com.github.zchu.archapp.user.moduleservice

import androidx.fragment.app.Fragment

interface MineFragmentCreator {
    fun createMineFragment(parameter: String): Fragment
}