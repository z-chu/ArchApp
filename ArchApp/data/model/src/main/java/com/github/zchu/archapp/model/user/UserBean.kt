package com.github.zchu.archapp.model.user

import com.google.gson.annotations.SerializedName
import java.util.*


data class UserBean(
    @SerializedName("objectId")
    var userId: String? = null,
    var sessionToken: String? = null,
    var nickname: String? = null,
    var username: String? = null,
    var birthday: Date? = null,
    var bio: String? = null,
    var gender: Int = 0,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var headPortrait: String? = null,
    var isEmailVerified: Boolean = false,
    var isMobilePhoneVerified: Boolean = false,
) {


    companion object {
        const val GENDER_UNKNOWN = 0
        const val GENDER_MALE = 1
        const val GENDER_FEMALE = 2
    }
}