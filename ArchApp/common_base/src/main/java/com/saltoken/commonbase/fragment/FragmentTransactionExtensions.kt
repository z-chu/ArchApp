package com.saltoken.commonbase.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentTransaction.addUnique(
    fragmentManager: FragmentManager,
    @IdRes containerViewId: Int,
    tag: String,
    fragmentCreator: () -> Fragment,
): FragmentTransaction {
    if (fragmentManager.findFragmentByTag(tag) == null) {
        add(containerViewId, fragmentCreator.invoke(), tag)
    }
    return this
}

fun FragmentTransaction.showOrAdd(
    fragmentManager: FragmentManager,
    @IdRes containerViewId: Int,
    tag: String,
    fragmentCreator: () -> Fragment,
): FragmentTransaction {
    val findFragmentByTag = fragmentManager.findFragmentByTag(tag)
    if (findFragmentByTag == null) {
        add(containerViewId, fragmentCreator.invoke(), tag)
    } else {
        show(findFragmentByTag)
    }
    return this
}

fun FragmentTransaction.remove(
    fragmentManager: FragmentManager,
    tag: String
): FragmentTransaction {
    val findFragmentByTag = fragmentManager.findFragmentByTag(tag)
    if (findFragmentByTag != null) {
        remove(findFragmentByTag)
    }
    return this
}