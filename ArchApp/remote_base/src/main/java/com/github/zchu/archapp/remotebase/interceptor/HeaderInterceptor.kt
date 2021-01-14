package com.github.zchu.archapp.remotebase.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor(private val headerMap: Map<String, String>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        for (entry in headerMap) {
            builder.addHeader(entry.key, entry.value)
        }
        return chain.proceed(builder.build())
    }
}