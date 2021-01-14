package com.github.zchu.archapp.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zchu.archapp.login.data.LoginDataSource
import com.github.zchu.archapp.login.data.bean.UserBean
import com.github.zchu.common.rx.RxViewModel
import com.github.zchu.model.WorkResult
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.rx.applySchedulers
import com.saltoken.commonbase.rx.subscribeTo

class LoginViewModel(
    private val loginDataSource: LoginDataSource,
    private val appSchedulers: AppSchedulers
) : RxViewModel() {

    private val _loginResult = MutableLiveData<WorkResult<UserBean>>()

    val loginResult: LiveData<WorkResult<UserBean>>
        get() = _loginResult

    fun loginOrRegister(username: String, password: String) {
        loginDataSource
            .login(username, password)
            .applySchedulers(appSchedulers)
            .subscribeTo(_loginResult)
            .disposeWhenCleared()
    }

}