package com.github.zchu.archapp.usersession

import android.os.Looper
import androidx.lifecycle.LiveData
import com.github.zchu.archapp.usersession.model.UserProfile
import com.github.zchu.archapp.usersession.model.UserProfileImpl
import com.github.zchu.archapp.usersession.model.getSimpleImpl
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

internal class UserProfilePreferences(private val mmkv: MMKV) {

    private val gson = Gson()


    private var userProfile: UserProfile?
        get() {
            val decodeString = mmkv.decodeString("user_profile_data", null)
            return if (decodeString != null) {
                gson.fromJson(decodeString, UserProfileImpl::class.java)
            } else {
                null
            }
        }
        set(value) {
            if (value != null) {
                mmkv.encode("user_profile_data", gson.toJson(value.getSimpleImpl()))
            } else {
                mmkv.removeValueForKey("user_profile_data")
            }
        }


    fun loadUserProfile(): UserProfile? {
        return userProfile
    }

    fun saveUserProfile(userProfile: UserProfile) {
        this.userProfile = userProfile
        _liveUserProfile.notifyDataSetChanged(userProfile)
    }


    fun clear() {
        mmkv.clearAll()
        if (_liveUserProfile.value != null) {
            _liveUserProfile.value = null
        }
    }

    val liveUserProfile: LiveData<UserProfile?>
        get() = _liveUserProfile


    private val _liveUserProfile: LiveUserProfile by lazy {
        LiveUserProfile(this)
    }

    private class LiveUserProfile(val userProfilePreferences: UserProfilePreferences) :
        LiveData<UserProfile?>() {

        init {
            callValue()
        }

        fun notifyDataSetChanged(userProfile: UserProfile? = null) {
            callValue(userProfile)
        }


        public override fun setValue(value: UserProfile?) {
            if (Looper.getMainLooper().thread === Thread.currentThread()) {
                super.setValue(value)
            } else {
                postValue(value)
            }
        }

        fun callValue(userProfile: UserProfile? = null) {
            value = userProfile ?: userProfilePreferences.loadUserProfile()
        }

    }


}