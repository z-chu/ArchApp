package com.saltoken.commonbase.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


inline fun FragmentManager.aloneShowDialogFragment(tag: String, block: () -> DialogFragment) {
    val dialogFragment = findFragmentByTag(tag) as? DialogFragment ?: block.invoke()
    if (!dialogFragment.isAdded) {
        dialogFragment.show(this, tag)
    }
}
