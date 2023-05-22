package com.example.wallpapertutorial.Service;

import static com.example.wallpapertutorial.Service.ApiUtilities.API;

import com.example.wallpapertutorial.Models.SesrchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = "https://api.pexels.com/v1/";

    @Headers("Authorization:"+ API)
    @GET("curated")
    Call<SesrchModel> getImage(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @Headers("Authorization:"+ API)
    @GET("search")
    Call<SesrchModel> getSearchImage(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int per_page
    );
}
