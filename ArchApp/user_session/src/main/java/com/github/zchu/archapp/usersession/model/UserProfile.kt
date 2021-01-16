package com.github.zchu.archapp.usersession.model

import java.util.*

interface UserProfile {
    val userId: String
    val username: String
    val nickname: String?
    var gender: Int
    var headPortrait: String?
    var bio: String?
    var birthday: Date?
    var isEmailVerified: Boolean
    var isMobilePhoneVerified: Boolean
    var createdAt: Date?
    var updatedAt: Date?


    companion object {
        const val GENDER_UNKNOWN = 0
        const val GENDER_MALE = 1
        const val GENDER_FEMALE = 2
    }
}