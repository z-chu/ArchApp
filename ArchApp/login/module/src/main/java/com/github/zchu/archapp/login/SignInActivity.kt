package com.github.zchu.archapp.login

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.view.KeyEvent
import androidx.annotation.MainThread
import com.dd.processbutton.iml.ActionProcessButton
import com.gelitenight.waveview.library.WaveView
import com.github.zchu.archapp.login.anim.WaveHelper
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.login.viewmodel.LoginViewModel
import com.github.zchu.archapp.model.user.UserBean
import com.github.zchu.archapp.moduleservice.MainActivityStarter
import com.github.zchu.common.help.showToastShort
import com.rengwuxian.materialedittext.MaterialEditText
import com.saltoken.common.base.BaseActivity
import com.saltoken.common.extensions.getEasyMessage
import com.saltoken.commonbase.models.observeWork
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class SignInActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()


    private lateinit var etUsername: MaterialEditText
    private lateinit var etPassword: MaterialEditText
    private lateinit var btnSignIn: ActionProcessButton
    private lateinit var mWaveHelper: WaveHelper

    private lateinit var dialog: Dialog

    private val mainActivityStarter: MainActivityStarter by inject()

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
                    showSuccess(it)
                },
                onError = {
                    showError(it.getEasyMessage(this))
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
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialogInterface, _, keyEvent ->
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

    private fun showSuccess(userBean: UserBean) {
        startPendingIntent()
        etUsername.isEnabled = true
        etPassword.isEnabled = true
        btnSignIn.progress = 100
        dialog.setOnDismissListener {
            mainActivityStarter.start(this)
            finish()
            overridePendingTransition(0, R.anim.zoom_out)
        }
        btnSignIn.postDelayed({ dialog.dismiss() }, 200)
        notifyLogged()
    }

    private fun showLoading() {
        etUsername.isEnabled = false
        etPassword.isEnabled = false
        btnSignIn.progress = 50
    }

    private fun showError(errorMsg: String?) {
        etUsername.isEnabled = true
        etPassword.isEnabled = true
        btnSignIn.progress = 0
        errorMsg?.let {
            showToastShort(it)
        }
    }


    private fun startPendingIntent() {
        val pendingActivityIntent = intent.getParcelableExtra<Intent>(K_PENDING_ACTIVITY_INTENT)
        if (pendingActivityIntent != null) {
            if (packageManager.resolveActivity(pendingActivityIntent, 0) != null) {
                startActivity(pendingActivityIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanCallback()
    }

    companion object : SignInActivityStarter {

        private const val K_PENDING_ACTIVITY_INTENT = "pending_activity_intent"

        private var callbackReference: WeakReference<(() -> Unit)>? = null

        override fun newIntent(context: Context, pendingActivityIntent: Intent?): Intent {
            val intent = Intent(context, SignInActivity::class.java)
            pendingActivityIntent?.let {
                intent.putExtra(K_PENDING_ACTIVITY_INTENT, it)
            }
            return intent
        }

        override fun start(context: Context, pendingActivityIntent: Intent?) {
            context.startActivity(newIntent(context, pendingActivityIntent))
        }


        /**
         * 限制只能在主线程调用，以防出现并发导致回调不正确
         */
        @MainThread
        override fun start(context: Context, callback: () -> Unit) {
            check(Looper.getMainLooper() == Looper.myLooper())
            callbackReference = WeakReference(callback)
            context.startActivity(newIntent(context))
        }

        @MainThread
        private fun notifyLogged() {
            check(Looper.getMainLooper() == Looper.myLooper())
            callbackReference?.get()?.invoke()
            callbackReference = null
        }

        @MainThread
        private fun cleanCallback() {
            check(Looper.getMainLooper() == Looper.myLooper())
            callbackReference = null
        }


    }

}