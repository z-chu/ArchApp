package com.github.zchu.archapp.moduleservice.mockkit

import android.app.Activity
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
    }
}