package com.saltoken.commonbase.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


class NullOnEmptyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Factory factory;
    private final Type type;
    private final Annotation[] annotations;
    private final Retrofit retrofit;

    NullOnEmptyResponseBodyConverter(@Nullable Factory factory, Type type, Annotation[] annotations, Retrofit retrofit) {
        this.factory = factory;
        this.type = type;
        this.annotations = annotations;
        this.retrofit = retrofit;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        if (value.contentLength() == 0) {
            return null;
        }
        return retrofit
                .<T>nextResponseBodyConverter(factory, type, annotations)
                .convert(value);
    }
}
