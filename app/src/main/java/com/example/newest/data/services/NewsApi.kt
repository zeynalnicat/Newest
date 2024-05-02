package com.example.newest.data.services

import com.example.newest.data.instance.API_KEY
import com.example.newest.data.instance.RetrofitInstance
import com.example.newest.domain.models.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTop(
        @Query("country") country: String = "us",
        @Query("apiKey") key: String = API_KEY,
        @Query("pageSize") size:Int = 20,
        @Query("page") page:Int = 1
    ): Response<NewsModel>


    @GET("top-headlines")
    suspend fun getByCategory(
        @Query("category") category: String = "general",
        @Query("apiKey") key: String = API_KEY, @Query("pageSize") size: Int = 80
    ): Response<NewsModel>


    @GET("top-headlines")
    suspend fun search(@Query("q") q: String, @Query("apiKey") key: String = API_KEY): Response<NewsModel>

}