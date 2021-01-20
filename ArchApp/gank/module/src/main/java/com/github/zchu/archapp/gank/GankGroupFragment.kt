package com.github.zchu.archapp.gank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.zchu.archapp.gakn.R
import com.github.zchu.common.help.BaseFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.saltoken.common.base.BaseFragment

class GankGroupFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_gank_group

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view
            .findViewById<View>(R.id.view_search)
            .setOnClickListener {
                //TODO 去搜索
            }
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        val tab: TabLayout = view.findViewById(R.id.tab)
        val adapter = BaseFragmentAdapter(childFragmentManager)
        adapter.titles = categoryNameArray
        val fragments =
            arrayOfNulls<Fragment>(categoryNameArray.size)

        for (i in categoryNameArray.indices) {
            fragments[i] = GankCategoryFragment.newInstance(
                categoryNameArray.get(i)
            )
        }
        adapter.fragments = fragments.requireNoNulls()
        viewPager.adapter = adapter
        //viewPager.adapter = GankFragmentAdapter(childFragmentManager)
        tab.setupWithViewPager(viewPager)
    }

    companion object {
        private val categoryNameArray = arrayOf("Android", "App", "iOS", "前端", "瞎推荐", "拓展资源")
    }
}