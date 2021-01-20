package com.github.zchu.archapp.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zchu.archapp.login.data.LoginRepository
import com.github.zchu.archapp.login.usecase.SaveSessionUseCase
import com.github.zchu.archapp.model.user.UserBean
import com.github.zchu.bridge._subscribe
import com.github.zchu.common.livedata.safeSetValue
import com.github.zchu.common.rx.RxViewModel
import com.github.zchu.model.Failure
import com.github.zchu.model.Loading
import com.github.zchu.model.Success
import com.github.zchu.model.WorkResult
import com.saltoken.commonbase.concurrent.AppSchedulers
import com.saltoken.commonbase.models.bindCanceler
import com.saltoken.commonbase.rx.applySchedulers

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val appSchedulers: AppSchedulers,
    private val saveSessionUseCase: SaveSessionUseCase
) : RxViewModel() {

    private val _loginResult = MutableLiveData<WorkResult<UserBean>>()

    val loginResult: LiveData<WorkResult<UserBean>>
        get() = _loginResult

    fun loginOrRegister(username: String, password: String) {
        loginRepository
            .loginOrRegister(username, password)
            .applySchedulers(appSchedulers)
            ._subscribe {
                _onSubscribe {
                    it.disposeWhenCleared()
                    _loginResult.safeSetValue(Loading<UserBean>().bindCanceler(it, _loginResult))

                }
                _onError {
                    _loginResult.safeSetValue(Failure(it))
                }

                _onNext {
                    saveSessionUseCase(it)
                    _loginResult.safeSetValue(Success(it))
                }
            }


    }

}