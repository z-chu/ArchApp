package com.github.zchu.archapp.gank.service

import androidx.fragment.app.Fragment

interface GankFragmentCreator {
    fun createFragment(): Fragment
}