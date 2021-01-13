package com.github.zchu.archapp.remotebase;

import androidx.annotation.Nullable;

import okhttp3.ResponseBody;

import org.jetbrains.annotations.NotNull;

import retrofit2.Converter;

import java.io.IOException;

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
