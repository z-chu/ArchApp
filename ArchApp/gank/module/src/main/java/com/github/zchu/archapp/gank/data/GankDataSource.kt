package com.github.zchu.archapp.gank.data

import com.github.zchu.archapp.gank.data.bean.CategoryResult
import io.reactivex.rxjava3.core.Observable


class GankDataSource(private val gankService: GankService) {

    fun fetchCategoryItems(
        categoryName: String,
        page: Int,
        size: Int
    ): Observable<List<CategoryResult.ResultsBean>> {
        return gankService
            .fetchCategoryItems(categoryName, size, page)
            .map {
                it.results
            }
    }

}