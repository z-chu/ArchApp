package com.github.zchu.archapp.login.data

import com.github.zchu.archapp.login.data.bean.UserBean
import com.github.zchu.archapp.login.data.bean.UserBody
import com.google.gson.Gson
import com.saltoken.common.model.LCResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException
import java.io.IOException
import java.util.*

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


    fun loginOrRegister(username: String, password: String): Observable<UserBean> {
        val userBody = UserBody(username, password)
        return register(userBody)
            .onErrorResumeNext {
                if (it is HttpException) {
                    if (it.code() == 400) {
                        try {
                            it.response()?.errorBody()?.string()?.let { json ->
                                val lcResult: LCResult<*> =
                                    gson.fromJson(json, LCResult::class.java)
                                if (lcResult.code == 202) {
                                    return@onErrorResumeNext login(username, password)
                                }
                            }
                        } catch (ignored: IOException) {
                        }
                    }

                }
                return@onErrorResumeNext Observable.error(it)
            }
    }

}