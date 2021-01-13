package com.github.zchu.archapp.di

import com.saltoken.commonbase.concurrent.AppSchedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.dsl.module

val appModule = module {

    /**
     * 全局线程控制
     */
    single { AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }


}

