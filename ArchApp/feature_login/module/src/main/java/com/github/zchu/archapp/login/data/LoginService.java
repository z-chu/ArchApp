package com.github.zchu.archapp.login.data;



import com.github.zchu.archapp.login.data.bean.UserBean;
import com.github.zchu.archapp.login.data.bean.UserBody;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : zchu
 * date   : 2017/11/7
 * desc   :
 */

public interface LoginService {

    String BASE_URL = "https://fn78orw2.api.lncld.net/1.1/";


    /**
     * 注册用户
     * username，password是必须字段
     */
    @POST("users")
    Observable<UserBean> register(@Body UserBody body);

    @POST("login")
    Observable<UserBean> login(@Query("username") String username, @Query("password") String password);



}
