package com.github.zchu.archapp.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.zchu.archapp.usersession.UserSessionManager
import com.github.zchu.common.help.initToolbar
import com.github.zchu.common.util.showOrHide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saltoken.common.base.BaseActivity
import org.koin.android.ext.android.inject

class SettingActivity : BaseActivity() {

    private val userSessionManager: UserSessionManager by inject()
    private lateinit var actionLogout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initToolbar("设置", toolbarId = R.id.toolbar)
        actionLogout = findViewById(R.id.action_logout)
        actionLogout
            .setOnClickListener {
                logout()
            }
        userSessionManager
            .liveSession
            .observe(this, Observer {
                actionLogout.showOrHide(it != null)
            })
    }

    private fun logout() {
        MaterialAlertDialogBuilder(this)
            .setTitle("注销")
            .setMessage("确定要退出登录吗？")
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton("确定") { _, _ ->
                userSessionManager.clear()
                finish()
            }
            .show()
    }
}