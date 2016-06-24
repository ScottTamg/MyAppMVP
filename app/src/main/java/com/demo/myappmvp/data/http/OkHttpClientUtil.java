package com.demo.myappmvp.data.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Txw on 2016/6/22.
 */
public class OkHttpClientUtil {

    private static OkHttpClientUtil mInstance;
    private OkHttpClient mOkHttpClient;


    private OkHttpClientUtil() {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

    }

    public static OkHttpClientUtil getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientUtil();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
