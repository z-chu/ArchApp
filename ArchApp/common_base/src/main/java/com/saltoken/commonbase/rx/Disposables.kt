package com.saltoken.commonbase.rx

import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.safeDispose(){
    if(!isDisposed) {
        dispose()
    }
}