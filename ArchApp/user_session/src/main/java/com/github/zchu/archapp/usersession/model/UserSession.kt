package com.github.zchu.archapp.usersession.model

interface UserSession {
    val userId: String
    val userName: String
    val sessionToken: String

}