package com.github.zchu.archapp.gank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.zchu.archapp.gank.data.bean.CategoryResult
import com.github.zchu.archapp.gank.viewmodel.GankCategoryViewModel
import com.github.zchu.archapp.moduleservice.WebActivityStarter
import com.github.zchu.common.util.bindArgument
import com.saltoken.common.listing.BaseListFragment
import com.saltoken.common.widget.ItemDecorations
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GankCategoryFragment : BaseListFragment<CategoryResult.ResultsBean>() {

    override lateinit var adapter: BaseQuickAdapter<CategoryResult.ResultsBean, *>

    private val categoryName: String by bindArgument(K_CATEGORY_NAME)

    private val viewModel: GankCategoryViewModel by viewModel {
        parametersOf(
            categoryName
        )
    }
    private val webActivityStarter: WebActivityStarter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = GankCategoryAdapter()
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(ItemDecorations.listDivider(requireContext()))
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            webActivityStarter.start(requireContext(), item.url, item.desc)
        }
        bindListing(viewModel.getListing())
    }

    companion object {
        private const val K_CATEGORY_NAME = "category_name"
        fun newInstance(categoryName: String): Fragment {
            val args = Bundle()
            args.putString(K_CATEGORY_NAME, categoryName)
            val fragment = GankCategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }


}