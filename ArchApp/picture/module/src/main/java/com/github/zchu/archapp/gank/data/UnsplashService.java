package com.github.zchu.archapp.gank.data;


import com.github.zchu.archapp.gank.data.bean.UnsplashImage;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashService {
    String BASE_URL = "https://api.unsplash.com/";

    @GET("photos")
    Observable<List<UnsplashImage>> listPhotos(@Query("page") int page, @Query("per_page") int perPage);
}
