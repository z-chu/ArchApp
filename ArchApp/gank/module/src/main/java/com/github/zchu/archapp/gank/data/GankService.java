package com.github.zchu.archapp.gank.data;


import com.github.zchu.archapp.gank.data.bean.CategoryResult;
import com.github.zchu.archapp.gank.data.bean.SearchResult;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author : zchu
 * date   : 2017/11/7
 * desc   :
 */

public interface GankService {


    @GET("data/{category}/{number}/{page}")
    Observable<CategoryResult> fetchCategoryItems(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<CategoryResult> getRandomBeauties(@Path("number") int number);

    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<SearchResult> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);


}
