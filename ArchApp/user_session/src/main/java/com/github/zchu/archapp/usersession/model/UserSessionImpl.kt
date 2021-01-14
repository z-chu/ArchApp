package com.github.zchu.archapp.usersession.model

data class UserSessionImpl(
    override val userId: String,
    override val userName: String,
    override val sessionToken: String,
) : UserSession