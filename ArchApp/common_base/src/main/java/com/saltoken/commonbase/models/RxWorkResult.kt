package com.saltoken.commonbase.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.zchu.common.livedata.safeSetValue
import com.github.zchu.model.Failure
import com.github.zchu.model.Loading
import com.github.zchu.model.Success
import com.github.zchu.model.WorkResult
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.CancellationException


@CheckReturnValue
inline fun <T> Observable<T>.subscribeTo(
    mutableLiveData: MutableLiveData<WorkResult<T>>,
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onFailure: (Throwable) -> Unit = {}
): Disposable {
    val loading = Loading<T>()
    mutableLiveData.safeSetValue(loading)
    val disposable = subscribe(
        {
            mutableLiveData.safeSetValue(Success(it))
            onSuccess.invoke(it)
        },
        {
            it.message?.let {
                Log.e(mutableLiveData.javaClass.name, it)
            }
            mutableLiveData.safeSetValue(Failure(it))
            onFailure.invoke(it)
        }
    )
    loading.canceler = {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        mutableLiveData.safeSetValue(
            Failure(
                CancellationException()
            )
        )
    }
    return disposable
}

@CheckReturnValue
inline fun <T> Flowable<T>.subscribeTo(
    mutableLiveData: MutableLiveData<WorkResult<T>>,
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onFailure: (Throwable) -> Unit = {}
): Disposable {
    val loading = Loading<T>()
    mutableLiveData.safeSetValue(loading)
    val subscribe = this.subscribe({
        mutableLiveData.safeSetValue(Success(it))
        onSuccess.invoke(it)
    }, {
        mutableLiveData.safeSetValue(Failure(it))
        onFailure.invoke(it)
    })
    loading.canceler = {
        if (!subscribe.isDisposed) {
            subscribe.dispose()
        }
        mutableLiveData.safeSetValue(
            Failure(
                CancellationException()
            )
        )
    }
    return subscribe
}
