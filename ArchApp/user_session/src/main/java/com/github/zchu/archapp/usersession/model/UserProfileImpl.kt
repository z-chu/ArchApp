package com.github.zchu.archapp.usersession.model

import java.util.*

data class UserProfileImpl(
    override val userId: String,
    override val nickname: String,
    override val username: String,
    override var gender: Int = 0,
    override var headPortrait: String? = null,
    override var bio: String? = null,
    override var birthday: Date? = null,
    override var isEmailVerified: Boolean = false,
    override var isMobilePhoneVerified: Boolean = false,
    override var createdAt: Date? = null,
    override var updatedAt: Date? = null
) : UserProfile {
}

internal fun UserProfile.getSimpleImpl(): UserProfileImpl {
    return UserProfileImpl(
        userId,
        nickname,
        username,
        gender,
        headPortrait,
        bio,
        birthday,
        isEmailVerified,
        isMobilePhoneVerified,
        createdAt,
        updatedAt
    )
}

