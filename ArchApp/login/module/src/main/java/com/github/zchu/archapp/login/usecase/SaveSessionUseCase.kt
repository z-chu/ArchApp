package com.github.zchu.archapp.login.usecase

import com.github.zchu.archapp.model.user.UserBean
import com.github.zchu.archapp.usersession.UserSessionManager
import com.github.zchu.archapp.usersession.model.UserProfileImpl
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
            //save userProfile

            userSessionManager
                .saveUserProfile(
                    UserProfileImpl(
                        userId,
                        username,
                        userBean.nickname,
                        userBean.gender,
                        userBean.headPortrait,
                        userBean.bio,
                        userBean.birthday,
                        userBean.isEmailVerified,
                        userBean.isMobilePhoneVerified,
                        userBean.createdAt,
                        userBean.updatedAt
                    )
                )

        }

    }


}