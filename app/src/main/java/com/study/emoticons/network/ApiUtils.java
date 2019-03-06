package com.study.emoticons.network;

import retrofit2.Retrofit;

public class ApiUtils {

    private static NetService api;
    private static Retrofit retrofit = RetrofitManager.getInstance().retrofit();

    public static NetService getAPI(){
        if (api == null){
            api = retrofit.create(NetService.class);
        }
        return api;
    }
}
