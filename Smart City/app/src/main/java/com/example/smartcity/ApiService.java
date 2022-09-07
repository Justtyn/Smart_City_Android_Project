package com.example.smartcity;

import com.example.smartcity.bean.NewsCategory;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.bean.RotationData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/prod-api/press/press/list")
    Call<NewsSearch> getNews();

    @GET("/prod-api/api/rotation/list")
    Call<RotationData> getRotationData();

    @GET("/prod-api/press/category/list")
    Call<NewsCategory> getNewsCategory();

    @GET("/prod-api/press/press/list")
    Call<NewsSearch> get10News(@Query("pageNum") int pageNum,
                               @Query("pageSize") int pageSize, @Query("type") int type
    );
}
