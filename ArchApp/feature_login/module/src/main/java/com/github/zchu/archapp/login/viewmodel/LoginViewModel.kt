package com.github.zchu.archapp.login.viewmodel

import androidx.lifecycle.ViewModel
import com.github.zchu.archapp.login.data.LoginService
import com.saltoken.commonbase.concurrent.AppSchedulers

class LoginViewModel(
    private val loginService: LoginService,
    private val appSchedulers: AppSchedulers
) : ViewModel() {

    fun login(username: String, password: String) {


    }

}