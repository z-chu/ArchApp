package com.github.zchu.archapp.gank

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class GankFragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager,
    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val categoryNameArray = arrayOf("Android", "App", "iOS", "前端", "瞎推荐", "拓展资源")

    override fun getCount(): Int {
        return categoryNameArray.size
    }

    override fun getItem(position: Int): Fragment {
        return GankCategoryFragment.newInstance(getPageTitle(position).toString())
    }

    override fun getPageTitle(position: Int): CharSequence {
        return categoryNameArray[position]

    }


}