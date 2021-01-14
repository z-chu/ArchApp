package com.saltoken.commonbase.models

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.zchu.model.Failure
import com.github.zchu.model.Loading
import com.github.zchu.model.Success
import com.github.zchu.model.WorkResult

open class WorkResultObserver<T> : Observer<WorkResult<T>> {

    final override fun onChanged(t: WorkResult<T>?) {
        when (t) {
            is Loading<T> -> onLoading(t.canceler)
            is Success<T> -> onSuccess(t.value)
            is Failure<T> -> onError(t.throwable)
        }
    }

    open fun onLoading(canceler: (() -> Unit)?) {
    }

    open fun onSuccess(value: T) {
    }

    open fun onError(throwable: Throwable) {
        throwable.message?.let {
            Log.e(this.javaClass.name, it)
        }

    }
}

inline fun <T> LiveData<WorkResult<T>>.observeWork(
    owner: LifecycleOwner,
    crossinline onLoading: (canceler: (() -> Unit)?) -> Unit = {},
    crossinline onError: (throwable: Throwable) -> Unit = {},
    crossinline onSuccess: (value: T) -> Unit = {}
) {
    this.observe(owner, object : WorkResultObserver<T>() {
        override fun onError(throwable: Throwable) {
            super.onError(throwable)
            onError.invoke(throwable)
        }

        override fun onLoading(canceler: (() -> Unit)?) {
            super.onLoading(canceler)
            onLoading.invoke(canceler)
        }

        override fun onSuccess(value: T) {
            super.onSuccess(value)
            onSuccess.invoke(value)
        }
    })
}