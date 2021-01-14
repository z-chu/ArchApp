package com.github.zchu.archapp.login

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import com.dd.processbutton.iml.ActionProcessButton
import com.gelitenight.waveview.library.WaveView
import com.github.zchu.archapp.login.anim.WaveHelper
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.login.viewmodel.LoginViewModel
import com.github.zchu.common.help.showToastLong
import com.github.zchu.common.help.showToastShort
import com.rengwuxian.materialedittext.MaterialEditText
import com.saltoken.common.base.BaseActivity
import com.saltoken.commonbase.models.observeWork
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()


    private lateinit var etUsername: MaterialEditText
    private lateinit var etPassword: MaterialEditText
    private lateinit var btnSignIn: ActionProcessButton
    private lateinit var mWaveHelper: WaveHelper

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        btnSignIn.setOnClickListener {
            login()
        }
        loginViewModel
            .loginResult
            .observeWork(this,
                onLoading = {
                    showLoading()
                },
                onSuccess = {
                    showToastLong("登录成功：$it")
                    showContent()
                },
                onError = {
                    showError(it.message)
                }
            )
    }

    private fun initView() {
        val waveView = findViewById<WaveView>(R.id.wave)
        waveView.waterLevelRatio = 0.5f
        waveView.setShapeType(WaveView.ShapeType.SQUARE)
        waveView.setBorder(0, 0)
        waveView.setWaveColor(
            Color.parseColor("#28e6ee9c"),
            Color.parseColor("#3ce6ee9c")
        )
        mWaveHelper = WaveHelper(waveView)
        mWaveHelper.start()
        dialog = Dialog(this, R.style.Theme_ArchApp_Dialog_LoginCard)
        dialog.setContentView(R.layout.layout_login_card)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialogInterface, i, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK) {
                dialogInterface.dismiss()
                return@OnKeyListener true
            }
            false
        })
        dialog.setCancelable(false)
        dialog.setOnDismissListener { finish() }
        dialog.show()
        etUsername = dialog.findViewById(R.id.et_username)
        etPassword = dialog.findViewById(R.id.et_password)
        btnSignIn = dialog.findViewById(R.id.btn_sign_in)
    }

    private fun login() {
        val username = etUsername.text.toString()
        if (username.isBlank()) {
            etUsername.error = "用户名不能为空"
            return
        }
        val password = etPassword.text.toString()
        if (password.isBlank()) {
            etPassword.error = "密码不能为空"
            return
        }
        if (password.length < 6) {
            etPassword.error = "请输入6到16位密码"
            return
        }
        loginViewModel.loginOrRegister(username, password)
    }


    override fun onResume() {
        super.onResume()
        mWaveHelper.start()
    }

    override fun onPause() {
        super.onPause()
        mWaveHelper.cancel()
    }

    fun showContent() {
        etUsername.isEnabled = true
        etPassword.isEnabled = true
        btnSignIn.progress = 100
        dialog.setOnDismissListener {
            finish()
            overridePendingTransition(0, R.anim.zoom_out)
        }
        btnSignIn.postDelayed({ dialog.dismiss() }, 200)
    }

    fun showLoading() {
        etUsername.isEnabled = false
        etPassword.isEnabled = false
        btnSignIn.progress = 50
    }

    fun showError(errorMsg: String?) {
        etUsername.isEnabled = true
        etPassword.isEnabled = true
        btnSignIn.progress = 0
        errorMsg?.let {
            showToastShort(it)
        }
    }


    companion object : SignInActivityStarter {

        override fun newIntent(context: Context, pendingIntent: Intent?): Intent {
            return Intent(context, SignInActivity::class.java)
        }

        override fun start(context: Context, pendingIntent: Intent?) {
            context.startActivity(newIntent(context, pendingIntent))
        }

    }

}