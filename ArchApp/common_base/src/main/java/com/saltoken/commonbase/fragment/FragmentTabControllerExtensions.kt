package com.saltoken.commonbase.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.setupFragmentTabController(
    @IdRes containerViewId: Int,
    tags: Array<String>,
    block: (tag: String) -> Fragment
): FragmentTabController {
    return FragmentTabController(this, containerViewId, tags, block)
}

fun FragmentManager.setupFragmentTabController(
    @IdRes containerViewId: Int,
    tagIds: IntArray,
    block: (tag: Int) -> Fragment
): FragmentTabController {
    return FragmentTabController(
        this,
        containerViewId,
        tagIds.map { it.toString() }.toTypedArray()
    ) { block.invoke(it.toInt()) }
}

