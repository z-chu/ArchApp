package com.github.zchu.archapp.usersession

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.getkeepsafe.relinker.ReLinker
import com.github.zchu.archapp.usersession.model.UserProfile
import com.github.zchu.archapp.usersession.model.UserSession
import com.tencent.mmkv.MMKV

class UserSessionManager internal constructor(context: Context) {

    private val userSessionPreferences = UserSessionPreferences(
        MMKV.mmkvWithID("user_session", MMKV.MULTI_PROCESS_MODE)
    )

    private val userProfilePreferences = UserProfilePreferences(
        MMKV.mmkvWithID("user_profile", MMKV.MULTI_PROCESS_MODE)
    )

    init {
        if (!isLoggedIn()) {
            userSessionPreferences.clear()
            userProfilePreferences.clear()
        }
    }

    private val onExpiredListeners = ArrayList<() -> Unit>()

    val liveSession: LiveData<UserSession?>
        get() = userSessionPreferences.liveSession

    val liveUserProfile: LiveData<UserProfile?>
        get() = userProfilePreferences.liveUserProfile

    fun isLoggedIn(): Boolean {
        return userSessionPreferences.hasSession()
    }


    fun loadSession(): UserSession? {
        return userSessionPreferences.loadSession()
    }

    fun saveSession(session: UserSession) {
        userSessionPreferences.saveSession(session)
    }

    fun loadUserProfile(): UserProfile? {
        return userProfilePreferences.loadUserProfile()
    }

    fun saveUserProfile(userProfile: UserProfile) {
        userProfilePreferences.saveUserProfile(userProfile)
    }


    /**
     * 如果收到token过期，则调用该方法
     */
    fun expire() {
        val hasSession = userSessionPreferences.hasSession()
        clear()
        if (hasSession) {
            for (onExpiredListener in onExpiredListeners) {
                onExpiredListener.invoke()
            }
        }
    }


    fun addOnExpiredListener(callback: () -> Unit) {
        onExpiredListeners.add(callback)
    }


    fun clear() {
        userSessionPreferences.clear()

    }

    inline fun <T> doCheckLoggedIn(block: (UserSession) -> T): T {
        return (loadSession() ?: error("未能找到 UserSession ,请登录后再继续")).let(block)
    }


    inline fun <T> doIfLoggedIn(block: (UserSession) -> T): T? {
        return loadSession()?.let(block)
    }

    inline fun <T> doIfNotLoggedIn(block: () -> T): T? {
        if (!isLoggedIn()) {
            return block.invoke()
        }
        return null
    }

    companion object {

        private const val TAG = "UserSessionManager"

        fun initialize(context: Context) {
            val dir: String = context.filesDir.absolutePath.toString() + "/mmkv"
            try {
                MMKV.initialize(dir)
            } catch (error: UnsatisfiedLinkError) {
                Log.e(TAG, error.message.toString())
                MMKV.initialize(dir) { libName -> ReLinker.loadLibrary(context, libName) }
            }

        }


    }
}