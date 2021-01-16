package com.github.zchu.archapp.login.data

import com.github.zchu.archapp.login.data.bean.UserBean
import com.github.zchu.archapp.login.data.bean.UserBody
import com.github.zchu.archapp.model.LCResult
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class LoginDataSource(private val loginService: LoginService) {

    private val gson = Gson()

    fun login(username: String, password: String): Observable<UserBean> {
        return loginService
            .login(username, password)
    }

    fun register(body: UserBody): Observable<UserBean> {
        body.headPortrait = mockHeadPortraits[Random().nextInt(mockHeadPortraits.size)]
        body.deviceModel = android.os.Build.MODEL
        body.bio = mockBios[Random().nextInt(mockBios.size)]
        return loginService
            .register(body)
    }


    fun loginOrRegister(username: String, password: String): Observable<UserBean> {
        val userBody = UserBody(username, password)
        return register(userBody)
            .onErrorResumeNext {
                if (it is HttpException) {
                    if (it.code() == 400) {
                        try {
                            it.response()?.errorBody()?.string()?.let { json ->
                                val lcResult: LCResult<*> =
                                    gson.fromJson(json, LCResult::class.java)
                                if (lcResult.code == 202) {
                                    return@onErrorResumeNext login(username, password)
                                }
                            }
                        } catch (ignored: IOException) {
                        }
                    }

                }
                return@onErrorResumeNext Observable.error(it)
            }
    }

    companion object {
        val mockHeadPortraits = listOf(
            "https://i0.hdslb.com/bfs/bangumi/image/e081b1eff17c1f6c61ac41e9112a16790ba74720.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/309651136aa074246e76a00d437519434520f1a6.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/65407c71382b940d91bd40c71dbdad2608faed2e.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/7f1953a217be2e8df8d6223d2eeeaf3b94243e01.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/1d703634cd3ee35b625bf882f27289db301cae63.jpg@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/31220048e2326b8190752b7f3575b4eca5ceb231.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/0d83b59cb610132916c9c708557a441835bd0732.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/78040fac01344efbdc011389005d3711fddb4628.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/771f7e80ef71b3844e5ad5c0a3c783c85c475ae2.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/3121473d5dd03a9bcccb8490034207e724e731b3.jpg@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/7238cbc13d106a1713636985ba06e56b2d7bb2a2.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/91892978edda2615108aab8443a5e2e96ea2f188.png@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/4143c286982fd8f9ad467af7b6e4a74aaee1c026.jpg@140w_140h_1c_100q.webp",
            "https://i0.hdslb.com/bfs/bangumi/image/aaea2a985185bf9246be480e736f519404872438.png@140w_140h_1c_100q.webp",
        )

        val mockBios = listOf(
            "所有的伟大，都源于一个勇敢的开始。",
            "我也想低調啊，可是石利不允許啊，她不允許啊。",
            "又怎样？还要给你搬个奖吗？",
            "生活，就是心怀最大的善意在荆棘中穿行。即使被刺伤，亦不改初衷。",
            "小样，来打我啊，我好怕怕呀。"
        )
    }
}