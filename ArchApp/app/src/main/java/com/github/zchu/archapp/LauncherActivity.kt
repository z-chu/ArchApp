package com.github.zchu.archapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.zchu.archapp.data.bean.Welcome
import com.github.zchu.archapp.viewmodel.LauncherViewModel
import com.github.zchu.common.help.ToastDef.showShort
import com.github.zchu.common.util.ViewUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saltoken.common.base.BaseActivity
import com.saltoken.common.extensions.getEasyMessage
import com.saltoken.commonbase.utils.BitmapUtil
import com.saltoken.commonbase.utils.DateUtil
import com.saltoken.commonbase.utils.MD5
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zchu on 16-11-22.
 */
class LauncherActivity : BaseActivity() {
    private lateinit var tvSkip: TextView
    private var timeLeft = 3
    private val rxPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    private val viewModel: LauncherViewModel by activityScope().viewModel(owner = {
        ViewModelOwner.from(
            this,
            this
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 修复按下HOME键，再次打开APP导致闪屏页重新启动问题
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(R.layout.activity_launcher)
        val sharedPreferences = getSharedPreferences("start-welcome", MODE_PRIVATE)
        val string = sharedPreferences.getString(K_WELCOMES, null)
        var welcome: Welcome? = null
        if (string != null) {
            try {
                val welcomes = Gson().fromJson<List<Welcome>>(
                    string,
                    object : TypeToken<List<Welcome?>?>() {}.type
                )
                welcome = welcomes[Random().nextInt(welcomes.size)]
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        initDisPlay()
        if (welcome == null) {
            loadWelcomeText();
            initDefaultWelcomeText()
        } else {
            initWelcomeText(welcome)
            val timeMillis = sharedPreferences.getLong(K_TIME_MILLIS, 0)
            if (timeMillis < DateUtil.getDayStartTimeMillis()) {
                loadWelcomeText();
            }
        }

        viewModel
            .countDown
            .observe(this, androidx.lifecycle.Observer {
                timeLeft = it
                if (it == 0) {
                    skip()
                } else {
                    tvSkip.text = getString(R.string.format_skip, it)
                }
            })
        viewModel
            .welcomes
            .observe(this, androidx.lifecycle.Observer {
                sharedPreferences
                    .edit()
                    .putString(K_WELCOMES, Gson().toJson(it))
                    .putLong(K_TIME_MILLIS, System.currentTimeMillis())
                    .apply()
            })
    }

    private fun initDisPlay() {
        tvSkip = findViewById(R.id.tv_skip)
        tvSkip.setOnClickListener { skip() }
        val contentView = findViewById<View>(R.id.content_view)
        val tvVersionName = findViewById<TextView>(R.id.tv_version_name)
        tvVersionName.text = BuildConfig.VERSION_NAME
    }

    private fun initDefaultWelcomeText() {
        val llWelcome = findViewById<View>(R.id.ll_welcome) as LinearLayout
        llWelcome.visibility = View.VISIBLE
        val tvDate = findViewById<TextView>(R.id.tv_date)
        val format2 = SimpleDateFormat("yyyy年MM月dd日，EEEE")
        tvDate.text = format2.format(Date())
        val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        llWelcome.startAnimation(animation)
        viewModel.startCountDown(timeLeft)

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initWelcomeText(welcome: Welcome) {
        val llContent = findViewById<View>(R.id.ll_content)
        llContent.visibility = View.VISIBLE
        llContent.setOnLongClickListener { v ->
            saveWelcomeText(welcome, v)
            true
        }
        val date = Date()
        val format = SimpleDateFormat("EEEE")
        val tvWeekContent = findViewById<TextView>(R.id.tv_week_content)
        tvWeekContent.text = format.format(date)
        val format2 = SimpleDateFormat("yyyy年MM月dd日")
        val tvDateContent = findViewById<TextView>(R.id.tv_date_content)
        tvDateContent.text = format2.format(date)
        val tvTextContent = findViewById<TextView>(R.id.tv_text_content)
        tvTextContent.text = welcome.content
        val fromStr = StringBuilder("摘自　")
        if (TextUtils.isEmpty(welcome.fromAuthor) && TextUtils.isEmpty(welcome.fromBook)) {
            fromStr.append("网络")
        } else if (TextUtils.isEmpty(welcome.fromAuthor) && !TextUtils.isEmpty(welcome.fromBook)) {
            fromStr.append("《")
            fromStr.append(welcome.fromBook)
            fromStr.append("》")
        } else if (!TextUtils.isEmpty(welcome.fromAuthor) && TextUtils.isEmpty(welcome.fromBook)) {
            fromStr.append(welcome.fromAuthor)
        } else {
            fromStr.append(welcome.fromAuthor)
            fromStr.append(" ")
            fromStr.append("《")
            fromStr.append(welcome.fromBook)
            fromStr.append("》")
        }
        val tvFromContent = findViewById<TextView>(R.id.tv_from_content)
        tvFromContent.text = fromStr.toString()
        val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        llContent.startAnimation(animation)
        timeLeft = (welcome.duration / 1000).toInt()
        viewModel.startCountDown(timeLeft)

        findViewById<View>(R.id.ll_version)
            .setOnTouchListener(OnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (timeLeft > 0) {
                            viewModel.stopCountDown()
                            showShort("已暂停，松手继续")
                        }
                        return@OnTouchListener true
                    }
                    MotionEvent.ACTION_UP -> {
                        if (timeLeft > 0) {
                            viewModel.startCountDown(timeLeft)

                            showShort("倒计时继续")
                        } else {
                            skip()
                        }
                        return@OnTouchListener true
                    }
                }
                false
            })
    }

    private fun saveWelcomeText(welcome: Welcome, view: View) {
        viewModel.stopCountDown()
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val bitmap = ViewUtil.captureView(view)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .flatMap(Function<Boolean, ObservableSource<File>> { aBoolean ->
                if (aBoolean) {
                    val directoryPictures =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    if (!directoryPictures.exists()) {
                        directoryPictures.mkdirs()
                    }
                    val file = File(
                        directoryPictures.path + "//"
                                + MD5.getMessageDigest(welcome.createdAt) + ".png"
                    )
                    try {
                        BitmapUtil.saveBitmap2PNG(bitmap, file)
                        return@Function Observable.just(file)
                    } catch (e: IOException) {
                        return@Function Observable.error<File>(e)
                    }
                }
                Observable.error(SecurityException("未授予 Manifest.permission.WRITE_EXTERNAL_STORAGE 权限"))
            })
            .subscribeBy(
                onError = {
                    showShort(it.getEasyMessage(this@LauncherActivity))
                    viewModel.startCountDown(timeLeft)
                },
                onNext = {
                    showShort("已保存到" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path)
                    val uri = Uri.fromFile(it)
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                    viewModel.startCountDown(timeLeft)
                }
            )
    }

    override fun onPause() {
        super.onPause()
        if (timeLeft > 0) {
            viewModel.stopCountDown()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startCountDown(timeLeft)

    }

    private fun startCountDown(second: Int) {
        viewModel.startCountDown(second)
    }


    private fun loadWelcomeText() {
        viewModel.fetchWelcomes()

    }

    private fun skip() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }


    companion object {
        private const val K_WELCOMES = "welcomes"
        private const val K_TIME_MILLIS = "time_millis"
    }
}