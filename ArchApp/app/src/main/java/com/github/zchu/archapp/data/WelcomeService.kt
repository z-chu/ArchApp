package com.github.zchu.archapp.data

import com.github.zchu.archapp.data.bean.Welcome
import com.github.zchu.archapp.model.LCResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface WelcomeService {

    @GET("classes/Welcome")
    fun fetchWelcomes(): Observable<LCResult<List<Welcome>>>

}