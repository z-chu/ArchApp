package com.github.zchu.archapp.usersession

import android.content.Context
import android.os.Looper
import androidx.lifecycle.LiveData
import com.github.zchu.archapp.usersession.model.UserSession
import com.github.zchu.archapp.usersession.model.UserSessionImpl
import com.tencent.mmkv.MMKV

private const val K_SESSION_VERSION = "session_version"
private const val K_USER_ID = "user_id"
private const val K_USER_NAME = "user_name"
private const val K_SESSION_TOKEN = "session_token"

internal class UserSessionPreferences(context: Context, private val mmkv: MMKV) {

    private var sessionVersion: Int
        get() = mmkv.getInt(K_SESSION_VERSION, 0)
        set(value) {
            mmkv.putInt(K_SESSION_VERSION, value)
        }

    private var userId: String?
        get() = mmkv.decodeString(K_USER_ID, null)
        set(value) {
            mmkv.encode(K_USER_ID, value)
        }

    private var userName: String?
        get() = mmkv.decodeString(K_USER_NAME, null)
        set(value) {
            mmkv.encode(K_USER_NAME, value)
        }

    private var sessionToken: String?
        get() = mmkv.decodeString(K_SESSION_TOKEN, null)
        set(value) {
            mmkv.encode(K_SESSION_TOKEN, value)
        }


    fun loadSession(): UserSession? {
        if (sessionVersion == 0) return null
        val userId = userId ?: return null
        val userName = userName ?: return null
        val sessionToken = sessionToken ?: return null
        return UserSessionImpl(
            userId,
            userName,
            sessionToken
        )
    }

    fun saveSession(session: UserSession) {
        userId = session.userId
        userName = session.userName
        sessionToken = session.sessionToken
        ++sessionVersion
        _liveSession.notifyDataSetChanged(session)
    }

    fun notifyDataSetChanged() {
        _liveSession.notifyDataSetChanged()
    }

    fun hasSession(): Boolean {
        return loadSession() != null
    }


    fun clear() {
        mmkv.clearAll()
        if (_liveSession.value != null) {
            _liveSession.value = null
        }
    }

    val liveSession: LiveData<UserSession?>
        get() = _liveSession


    private val _liveSession: LiveUserSession by lazy {
        LiveUserSession(this)
    }

    private class LiveUserSession(val userPreferences: UserSessionPreferences) :
        LiveData<UserSession?>() {

        init {
            callValue()
        }

        fun notifyDataSetChanged(session: UserSession? = null) {
            callValue(session)
        }


        public override fun setValue(value: UserSession?) {
            if (Looper.getMainLooper().thread === Thread.currentThread()) {
                super.setValue(value)
            } else {
                postValue(value)
            }
        }

        fun callValue(session: UserSession? = null) {
            value = session ?: userPreferences.loadSession()
        }

    }


}