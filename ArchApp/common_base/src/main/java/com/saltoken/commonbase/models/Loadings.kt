package com.saltoken.commonbase.models

import androidx.lifecycle.MutableLiveData
import com.github.zchu.common.livedata.safeSetValue
import com.github.zchu.model.Failure
import com.github.zchu.model.Loading
import com.github.zchu.model.WorkResult
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.CancellationException

fun <T> Loading<T>.bindCanceler(
    disposable: Disposable,
    mutableLiveData: MutableLiveData<WorkResult<T>>
): Loading<T> {
    val loading = Loading<T>()
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
    return loading
}