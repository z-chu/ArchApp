package com.github.zchu.archapp.login.data.bean

/**
 * Created by Chu on 2017/11/26.
 */
class UserBody(var username: String, var password: String) {
    var headPortrait: String? = null
    var deviceModel: String? = null
    var bio: String? = null
}