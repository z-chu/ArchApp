package com.github.zchu.archapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.github.zchu.archapp.moduleservice.WebActivityStarter
import com.github.zchu.common.help.initToolbar
import com.github.zchu.common.help.showToastShort
import com.github.zchu.common.util.bindExtra
import com.saltoken.common.base.BaseActivity

/**
 * author : zchu
 * date   : 2017/12/22
 * desc   :
 */
class WebActivity : BaseActivity() {
    private lateinit var mWebView: WebView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mToolbar: Toolbar

    private val title: String by bindExtra(EXTRA_TITLE)
    private val url: String by bindExtra(EXTRA_URL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        mToolbar = initToolbar(title, toolbarId = R.id.toolbar)
        mWebView = findViewById(R.id.web_view)
        mProgressBar = findViewById(R.id.progress_bar)
        val settings = mWebView.getSettings()
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        mWebView.webChromeClient = ChromeClient()
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_refresh -> {
                mWebView.reload()
                return true
            }
            R.id.action_copy_url -> {
                val clipData = ClipData.newPlainText("youshu_copy", mWebView!!.url)
                val manager = this.getSystemService(
                    CLIPBOARD_SERVICE
                ) as ClipboardManager
                manager.setPrimaryClip(clipData)
                showToastShort(R.string.tip_copy_done)
                return true
            }
            R.id.action_open_url -> {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                val uri = Uri.parse(getIntent().getStringExtra(EXTRA_URL))
                intent.data = uri
                if (deviceCanHandleIntent(this, intent)) {
                    startActivity(intent)
                } else {
                    showToastShort(R.string.tip_open_fail)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deviceCanHandleIntent(context: Context, intent: Intent): Boolean {
        return try {
            context.packageManager.queryIntentActivities(intent, 0).isNotEmpty()
        } catch (e: NullPointerException) {
            false
        }
    }

    override fun onDestroy() {

        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
        // destory()
        val parent = mWebView.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(mWebView)
        }
        mWebView.stopLoading()
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebView.settings.javaScriptEnabled = false
        mWebView.clearHistory()
        mWebView.clearView()
        mWebView.removeAllViews()
        mWebView.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mWebView.onResume()
    }

    private inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            mProgressBar.progress = newProgress
            if (newProgress == 100) {
                mProgressBar.visibility = View.GONE
            } else {
                mProgressBar.visibility = View.VISIBLE
            }
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            mToolbar.title = title
        }
    }

    companion object : WebActivityStarter {
        private const val EXTRA_URL = "extra_url"
        private const val EXTRA_TITLE = "extra_title"

        override fun newIntent(context: Context, url: String, title: String?): Intent {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title ?: (context.getString(R.string.abc_app_name)))
            return intent
        }

        override fun start(context: Context, url: String, title: String?) {
            context.startActivity(newIntent(context, url, title))
        }
    }
}