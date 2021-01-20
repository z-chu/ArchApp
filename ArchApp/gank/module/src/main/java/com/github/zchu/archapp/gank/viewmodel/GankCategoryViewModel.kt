package com.github.zchu.archapp.gank.viewmodel

import com.github.zchu.archapp.gank.data.GankDataSource
import com.github.zchu.archapp.gank.data.bean.CategoryResult
import com.saltoken.common.listing.AbsRxListingModel
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.rx.applySchedulers
import io.reactivex.rxjava3.core.Observable

class GankCategoryViewModel(
    private val categoryName: String,
    private val gankDataSource: GankDataSource,
    private val appSchedulers: AppSchedulers
) : AbsRxListingModel<CategoryResult.ResultsBean>() {

    override fun doLoadInitial(
        isRefresh: Boolean,
        initialSize: Int
    ): Observable<List<CategoryResult.ResultsBean>> {
        return gankDataSource
            .fetchCategoryItems(categoryName, 1, initialSize)
            .applySchedulers(appSchedulers)
    }

    override fun doLoadMore(
        currentData: List<CategoryResult.ResultsBean>,
        moreSize: Int
    ): Observable<List<CategoryResult.ResultsBean>> {
        return gankDataSource
            .fetchCategoryItems(categoryName, getDataSize() / moreSize + 1, moreSize)
            .applySchedulers(appSchedulers)
    }
}