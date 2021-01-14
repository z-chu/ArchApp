package com.github.zchu.archapp.login.usecase

import com.github.zchu.archapp.login.data.bean.UserBean
import com.github.zchu.archapp.usersession.UserSessionManager
import com.github.zchu.archapp.usersession.model.UserSessionImpl

class SaveSessionUseCase(private val userSessionManager: UserSessionManager) {

    operator fun invoke(userBean: UserBean) {
        val userId = userBean.userId
        val username = userBean.username
        val sessionToken = userBean.sessionToken
        if (userId != null && username != null && sessionToken != null) {
            userSessionManager.saveSession(
                UserSessionImpl(
                    userId,
                    username,
                    sessionToken
                )
            )
        }

    }


}