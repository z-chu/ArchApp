package com.saltoken.common.extensions

import android.content.Context
import android.net.ParseException
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.saltoken.common.R
import com.saltoken.common.model.LCErrorBody
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.StreamResetException
import org.json.JSONException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Throwable?.getEasyMessage(
    context: Context,
    defaultMessage: String = context.getString(R.string.exception_default)
): String {
    if (this == null) {
        return defaultMessage
    }
    val localMessage = this.message
    return if (this is SocketTimeoutException) {
        context.getString(R.string.exception_socket_time_out)
    } else if (this is ConnectException) {
        context.getString(R.string.exception_connect)
    } else if (this is retrofit2.HttpException) {
        val response = this.response()
        val responseBody = response!!.errorBody()
        if (responseBody != null) {
            try {
                val errorBody: LCErrorBody =
                    Gson().fromJson(responseBody.string(), LCErrorBody::class.java)
                if (!TextUtils.isEmpty(errorBody.error)) {
                    return errorBody.error
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (response.code() == 502) {
            return context.getString(R.string.error_http_code_502)
        }
        this.message ?: context.getString(R.string.error_server)
    } else if (this is UnknownHostException) {
        context.getString(R.string.exception_unknown_host)
    } else if (this is SecurityException) {
        context.getString(R.string.exception_security)
    } else if (this is JsonParseException
        || this is JSONException
        || this is ParseException
    ) {
        context.getString(R.string.exception_json)
    } else if (this is javax.net.ssl.SSLHandshakeException) {
        context.getString(R.string.exception_ssl)
    } else if (this is java.util.concurrent.TimeoutException) {
        context.getString(R.string.exception_timeout)
    } else if (this is javax.net.ssl.SSLException) {
        context.getString(R.string.exception_connection_was_rest)
    } else if (this is StreamResetException) {
        if (errorCode == ErrorCode.PROTOCOL_ERROR) {
            context.getString(R.string.exception_protocol_error)
        } else {
            context.getString(R.string.exception_connect)
        }
    } else if (this is IOException) {
        defaultMessage
    } else {
        localMessage ?: defaultMessage
    }
}