package com.example.wallpapertutorial;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {
    private  static  Retrofit retrofit = null;
    public static final  String API  = "563492ad6f91700001000001f99186aecc0b4d5f84602f4312cfc11e";

    public static ApiInterface getApiInterface()
    {
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return  retrofit.create(ApiInterface.class);
    }
}
