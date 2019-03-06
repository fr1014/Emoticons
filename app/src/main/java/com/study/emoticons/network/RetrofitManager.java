package com.study.emoticons.network;
import com.study.emoticons.constans.URLConstans;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final int CONNECT_TIME_OUT = 10; //连接超时时长
    private static final int READ_TIME_OUT = 10; //读数据超时时长
    private static final int WRITE_TIME_OUT = 10; //写数据超时时长
    public static String BASE_URL = URLConstans.API_BASE_URL;

    private volatile static RetrofitManager instance;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    /**
     *
     * 初始化okHttp
     */
    private static OkHttpClient okHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 初始化Retrofit
     */
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
