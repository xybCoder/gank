package com.xybcoder.gank.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 *
 * Created by xybcoder on 16/3/1.
 */
public class GankClient {
    public static final String HOST = "http://gank.io/api/";
    private static GankRetrofit gankRetrofit;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;

    private GankClient(){

    }

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static GankRetrofit getGankRetrofitInstance() {
        synchronized (monitor) {
            if (gankRetrofit == null) {
                gankRetrofit = retrofit.create(GankRetrofit.class);
            }
            return gankRetrofit;
        }
    }
}
