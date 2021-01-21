package com.github.zchu.archapp.gank

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.zchu.archapp.gank.data.bean.UnsplashImage
import com.github.zchu.archapp.gank.viewmodel.UnsplashViewModel
import com.saltoken.common.listing.BaseListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnsplashFragment : BaseListFragment<UnsplashImage>() {

    override lateinit var adapter: BaseQuickAdapter<UnsplashImage, *>


    private val viewModel: UnsplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = UnsplashAdapter()
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnItemClickListener { _, _, position ->
            //TODO 查看大图
        }
        bindListing(viewModel.getListing())
    }


}