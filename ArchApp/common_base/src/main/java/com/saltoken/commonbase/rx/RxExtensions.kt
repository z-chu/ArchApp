package com.saltoken.commonbase.rx

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zchu.common.livedata.safeSetValue
import com.github.zchu.model.Failure
import com.github.zchu.model.Loading
import com.github.zchu.model.Success
import com.github.zchu.model.WorkResult
import com.saltoken.commonbase.concurrent.AppSchedulers
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.CancellationException

fun <T> Observable<T>.asLiveDataOfViewData(): ResourceLiveData<T> {
    return ResourceLiveData(this)
}

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


fun <T> Observable<T>.applySchedulers(appSchedulers: AppSchedulers): Observable<T> {
    return this
        .subscribeOn(appSchedulers.io)
        .observeOn(appSchedulers.main)
}

fun <T> Flowable<T>.applySchedulers(appSchedulers: AppSchedulers): Flowable<T> {
    return this
        .subscribeOn(appSchedulers.io)
        .observeOn(appSchedulers.main)
}


class ResourceLiveData<T>(private val observable: Observable<T>) : LiveData<WorkResult<T>>(),
    Disposable {

    @Volatile
    private var disposable: Disposable? = null

    private var isDisposed: Boolean = false
    private var isSubscribed: Boolean = false

    override fun isDisposed(): Boolean {
        return disposable?.isDisposed ?: isDisposed
    }

    override fun dispose() {
        disposable?.dispose()
        isDisposed = true
    }

    private fun subscribe() {
        if (!isDisposed) {
            observable
                .subscribe(object : Observer<T> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        safeSetValue(Loading())
                    }

                    override fun onNext(t: T) {
                        safeSetValue(Success(t))
                    }

                    override fun onError(e: Throwable) {
                        safeSetValue(Failure(e))
                    }

                    override fun onComplete() {
                    }

                })
        }
    }


    private fun safeSetValue(value: WorkResult<T>) {
        if (Looper.getMainLooper().thread === Thread.currentThread()) {
            setValue(value)
        } else {
            postValue(value)
        }
    }


    override fun onActive() {
        super.onActive()
        if (!isSubscribed) {
            subscribe()
            isSubscribed = true
        }
    }


}