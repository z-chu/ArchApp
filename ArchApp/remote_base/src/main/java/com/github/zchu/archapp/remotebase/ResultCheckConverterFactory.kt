package io.bithumb.android.remote.converter

import com.github.zchu.archapp.remotebase.ResultCheckBodyConverter
import com.github.zchu.archapp.remotebase.ResultChecker
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class ResultCheckConverterFactory(
    private val factory: Converter.Factory,
    private val resultChecker: ResultChecker?=null
) :
    Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return ResultCheckBodyConverter(
            resultChecker,
            factory.responseBodyConverter(
                type,
                annotations,
                retrofit
            )
        )
    }
}