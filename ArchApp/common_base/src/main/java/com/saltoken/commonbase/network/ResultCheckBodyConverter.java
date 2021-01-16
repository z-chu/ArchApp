package com.saltoken.commonbase.network;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ResultCheckBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Converter<ResponseBody, T> converter;
    @Nullable
    private final ResultChecker resultChecker;

    public ResultCheckBodyConverter(@Nullable ResultChecker resultChecker, Converter<ResponseBody, T> converter) {
        this.resultChecker = resultChecker;
        this.converter = converter;
    }

    @Nullable
    @Override
    public T convert(@NotNull ResponseBody value) throws IOException {
        T result = converter.convert(value);
        if(resultChecker!=null) {
            resultChecker.onResultConverted(result);
        }
        return result;
    }

}
