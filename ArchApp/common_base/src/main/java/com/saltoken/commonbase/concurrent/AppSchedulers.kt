package com.saltoken.commonbase.concurrent

import io.reactivex.rxjava3.core.Scheduler

class AppSchedulers(
    val main: Scheduler,
    val io: Scheduler
)