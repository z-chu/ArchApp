package com.saltoken.commonbase.concurrent

import java.util.concurrent.Executor

class AppExecutors (
    val main: Executor,
    val io: Executor
)