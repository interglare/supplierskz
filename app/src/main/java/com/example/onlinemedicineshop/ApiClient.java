package com.example.onlinemedicineshop;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    public static final String BASE_URL="http://192.168.1.15/OnlineMedicineShopMobile/";
    public static final String BASE_URL="https://oninemedicineshop.000webhostapp.com/OnlineMedicineShopMobile/";
    private static Retrofit retrofit=null;

    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;

    }
}
