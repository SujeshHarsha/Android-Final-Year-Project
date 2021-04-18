package com.example.myfirst.assignment.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseUrl {
    public static String Base_Url ="http://10.0.2.2:3012/api/v1/";
    public static String Token=null;
    public static String Status=null;
    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
