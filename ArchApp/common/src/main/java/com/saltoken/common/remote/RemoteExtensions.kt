package com.saltoken.common.remote

import android.content.Context
import com.saltoken.commonbase.network.NullOnEmptyConverterFactory
import com.saltoken.commonbase.network.ResultCheckConverterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*


fun createOkHttpClient(
    context: Context,
    isDebug: Boolean,
    name: String,
    block: (OkHttpClient.Builder) -> Unit = {}
): OkHttpClient {
    val builder = OkHttpClient.Builder()

    builder.cache(createRemoteCache(context, name))
    block.invoke(builder)
    if (isDebug) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
    builder.protocols(Collections.singletonList(Protocol.HTTP_1_1))
    return builder.build()
}

/**
 * @param maxSize 最大缓存大小。单位:字节。 默认 20M
 */
fun createRemoteCache(context: Context, name: String, maxSize: Long = 20971520): Cache {
    val cacheFile = File(context.cacheDir, "${name}RemoteCache")
    val cache = Cache(cacheFile, maxSize)
    return cache;
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(
            ResultCheckConverterFactory(
                GsonConverterFactory.create()
            )
        )
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
        .build()
        .create(T::class.java)
}
