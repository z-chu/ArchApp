package com.github.zchu.archapp.gank.viewmodel


import com.github.zchu.archapp.gank.data.UnsplashDataSource
import com.github.zchu.archapp.gank.data.bean.UnsplashImage
import com.saltoken.common.listing.AbsRxListingModel
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.rx.applySchedulers
import io.reactivex.rxjava3.core.Observable

class UnsplashViewModel(
    private val unsplashDataSource: UnsplashDataSource,
    private val appSchedulers: AppSchedulers
) : AbsRxListingModel<UnsplashImage>() {

    override fun doLoadInitial(
        isRefresh: Boolean,
        initialSize: Int
    ): Observable<List<UnsplashImage>> {
        return unsplashDataSource
            .listPhotos(1, initialSize)
            .applySchedulers(appSchedulers)
    }

    override fun doLoadMore(
        currentData: List<UnsplashImage>,
        moreSize: Int
    ): Observable<List<UnsplashImage>> {
        return unsplashDataSource
            .listPhotos(getDataSize() / moreSize + 1, moreSize)
            .applySchedulers(appSchedulers)
    }
}