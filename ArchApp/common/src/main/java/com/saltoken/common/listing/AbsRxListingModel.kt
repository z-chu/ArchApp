package com.saltoken.common.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zchu.common.rx.KeepLastDisposable
import com.github.zchu.listing.AbsListingModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class AbsRxListingModel<T>
@JvmOverloads
constructor(private val initialSize: Int = 15, private val moreSize: Int = 15) :
    AbsListingModel<T>() {

    private val keepLastDisposable = KeepLastDisposable()

    @Volatile
    var dataTotal: Int? = null
        set(value) {
            field = value
            if (mDataTotalLiveData.value != dataTotal) {
                mDataTotalLiveData.postValue(dataTotal)
            }
        }
    private val mDataTotalLiveData = MutableLiveData<Int>()
    val dataTotalLiveData: LiveData<Int> = mDataTotalLiveData

    override fun doLoadInitial(isRefresh: Boolean, callback: LoadCallback<T>) {
        doLoadInitial(isRefresh, initialSize)
            .subscribe(object : Observer<List<T>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    keepLastDisposable.add("LoadInitial", d)
                }

                override fun onNext(t: List<T>) {
                    callback.onResult(t)
                }

                override fun onError(e: Throwable) {
                    callback.onFailure(e)
                }


            })

    }

    override fun doLoadMore(callback: LoadCallback<T>) {
        doLoadMore(getData(), moreSize)
            .subscribe(object : Observer<List<T>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    keepLastDisposable.add("LoadMore", d)
                }

                override fun onNext(t: List<T>) {
                    callback.onResult(t)
                }

                override fun onError(e: Throwable) {
                    callback.onFailure(e)
                }


            })


    }

    abstract fun doLoadInitial(isRefresh: Boolean, initialSize: Int): Observable<List<T>>

    abstract fun doLoadMore(currentData: List<T>, moreSize: Int): Observable<List<T>>


    override fun isNoMoreOnInitial(initialData: List<T>): Boolean {
        return initialData.size < initialSize || dataTotal?.let { getDataSize() >= it } ?: false

    }

    override fun isNoMoreOnMore(moreData: List<T>): Boolean {
        return moreData.size < moreSize || dataTotal?.let { getDataSize() >= it } ?: false
    }

    override fun onCleared() {
        super.onCleared()
        keepLastDisposable.dispose()
    }
}