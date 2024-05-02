package com.example.newest.data.instance

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val URL = "https://newsapi.org/v2/"
const val API_KEY = "f37818fdd1c844c4a17f3362579c6130"

object RetrofitInstance {
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {

        if (retrofit == null) {
            retrofit =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL)
                    .build()
        }
        return retrofit!!

    }


}



