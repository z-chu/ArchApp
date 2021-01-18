package com.github.zchu.archapp.moduleservice.mockkit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class MockActivity : Activity() {

    private val tvMessage: TextView by lazy { findViewById<TextView>(R.id.tv_message) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock)
        val stringBuilder = StringBuilder()
        stringBuilder.append("真正的组件没有被依赖，这是一个虚假的Activity")
        stringBuilder.append("\n")
        stringBuilder.append("Intent:$intent")
        stringBuilder.append("\n")
        val message = intent.getStringExtra("message")
        if (message != null) {
            stringBuilder.append("message:$message")
        }
        tvMessage.text = stringBuilder.toString()
    }

    companion object {
        fun newIntent(context: Context, message: String? = null): Intent {
            val intent = Intent(context, MockActivity::class.java)
            if (message != null) {
                intent.putExtra("message", message)
            }
            return intent
        }
    }
}