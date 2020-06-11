package com.wangtianya.qnet.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class QRetrofit {
    public static final String defaultUrl = "https://qjuzi.com";

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getClient() {
        if (okHttpClient== null) {
            synchronized(QRetrofit.class) {
                if (okHttpClient== null) {
                    okHttpClient = new OkHttpClient.Builder().build();
                }
            }
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit() {
        if (retrofit== null) {
            synchronized(QRetrofit.class) {
                if (retrofit== null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(defaultUrl)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static <T> T getService(Class<T> tClass) {
        T t = getRetrofit().create(tClass);
        return t;
    }
}
