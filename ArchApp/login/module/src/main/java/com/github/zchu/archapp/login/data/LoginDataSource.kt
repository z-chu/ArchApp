package com.github.zchu.archapp.login.data

import com.github.zchu.archapp.login.data.bean.UserBody
import com.github.zchu.archapp.model.user.UserBean
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable

class LoginDataSource(private val loginService: LoginService) {

    private val gson = Gson()

    fun login(username: String, password: String): Observable<UserBean> {
        return loginService
            .login(username, password)
    }

    fun register(body: UserBody): Observable<UserBean> {
        return loginService
            .register(body)
    }




}