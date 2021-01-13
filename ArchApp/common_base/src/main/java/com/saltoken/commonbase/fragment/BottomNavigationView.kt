package com.saltoken.commonbase.fragment

import android.os.Bundle
import androidx.annotation.CheckResult
import androidx.annotation.IdRes
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

@CheckResult(suggest = "请在拿到 FragmentTabController 后手动调用 saveInstanceState 方法保持状态")
fun BottomNavigationView.setupWithFragmentTabController(
    @IdRes containerViewId: Int,
    fragmentManager: FragmentManager,
    savedInstanceState: Bundle? = null,
    @IdRes defaultItemId: Int? = null,
    block: (itemId: Int) -> Fragment
) : FragmentTabController {
    val menu = this.menu
    val itemsSize = menu.size()
    check(itemsSize!=0){"menu items can't be empty"}
    val itemIds =IntArray(itemsSize)
    menu.forEachIndexed { index, item ->
        itemIds[index]=item.itemId
    }
    val fragmentTabController =
        fragmentManager.setupFragmentTabController(containerViewId, itemIds,block)
    fragmentTabController.restoreInstanceState(savedInstanceState,defaultItemId ?: itemIds[0])
    this.setOnNavigationItemSelectedListener {
        if (it.itemId.toString() != fragmentTabController.selectedFragmentTag) {
            fragmentTabController.select(it.itemId)
            return@setOnNavigationItemSelectedListener  true
        }
        false
    }
    return fragmentTabController
}
