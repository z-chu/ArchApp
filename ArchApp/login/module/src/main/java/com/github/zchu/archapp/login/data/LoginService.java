package com.github.zchu.archapp.login.data;



import com.github.zchu.archapp.login.data.bean.UserBody;
import com.github.zchu.archapp.model.user.UserBean;

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


    /**
     * 注册用户
     * username，password是必须字段
     */
    @POST("users")
    Observable<UserBean> register(@Body UserBody body);

    @POST("login")
    Observable<UserBean> login(@Query("username") String username, @Query("password") String password);



}
