package com.saltoken.commonbase.fragment

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

class FragmentTabController(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerViewId: Int,
    private val tags: Array<String>,
    private val block: (tag: String) -> Fragment
) {

    var selectedFragmentTag: String? = null
        private set(value) {
            if (value != field) {
                if (!tags.contains(value)) {
                    throw  IllegalStateException("Tags array must contains value")
                }
                field = value
                selectFragmentDisplay(fragmentManager, value)
                onSelectedFragmentTagChangeListener?.invoke(value)
            }
        }


    var onSelectedFragmentTagChangeListener: ((String?) -> Unit)? = null

    private val saveStateSelectedTagKey =
        "fragment_tab_controller_selected_fragment_tag_$containerViewId"


    private fun selectFragmentDisplay(
        fragmentManager: FragmentManager,
        tag: String?
    ) {
        val beginTransaction = fragmentManager.beginTransaction()
        val fragments = fragmentManager.fragments
        var isSelectFinish = false
        for (fragment in fragments) {
            if (fragment.tag == tag) {
                beginTransaction.show(fragment)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                isSelectFinish = true
            } else if (!fragment.isHidden && tags.contains(fragment.tag)) {
                beginTransaction.hide(fragment)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
        }
        if (!isSelectFinish && tag != null) {
            val fragment = block.invoke(tag)
            beginTransaction.add(containerViewId, fragment, tag)
            beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
        }
        try {
            beginTransaction.commitNowAllowingStateLoss()
        } catch (e: IllegalStateException) {
            // Workaround for Robolectric running measure/layout
            // calls inline rather than allowing them to be posted
            // as they would on a real device.
            beginTransaction.commitAllowingStateLoss()
        }
    }

    fun select(tag: String) {
        selectedFragmentTag = tag
    }

    fun select(tagId: Int) {
        select(tagId.toString())
    }

    fun saveInstanceState(outState: Bundle) {
        outState.putString(saveStateSelectedTagKey, selectedFragmentTag)
    }

    fun restoreInstanceState(savedInstanceState: Bundle?, defaultFragmentTag: String) {
        selectedFragmentTag =
            savedInstanceState?.getString(saveStateSelectedTagKey, defaultFragmentTag)
                ?: defaultFragmentTag
    }

    fun restoreInstanceState(savedInstanceState: Bundle?, defaultFragmentTag: Int) {
        restoreInstanceState(savedInstanceState, defaultFragmentTag.toString())
    }


}