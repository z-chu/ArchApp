package com.github.zchu.archapp.gank.data

import com.github.zchu.archapp.gank.data.bean.UnsplashImage
import io.reactivex.rxjava3.core.Observable


class UnsplashDataSource(private val unsplashService: UnsplashService) {


    fun listPhotos(
        page: Int,
        size: Int
    ): Observable<List<UnsplashImage>> {
        return unsplashService
            .listPhotos(page, size)
    }

}