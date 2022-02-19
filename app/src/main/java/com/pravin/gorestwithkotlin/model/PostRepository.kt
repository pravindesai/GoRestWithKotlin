package com.pravin.gorestwithkotlin.model

import com.pravin.gorestwithkotlin.pojo.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PostRepository {
    private val BASE_URL = "https://gorest.co.in/public/v1/"
    private val restService: GoRestService
    private val interceptor:HttpLoggingInterceptor
    private val client:OkHttpClient
    private val retrofit:Retrofit
    init {
        interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        client = OkHttpClient.Builder().addInterceptor(interceptor)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build()

        retrofit   = Retrofit.Builder().baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        restService = retrofit.create(GoRestService::class.java)
    }

    fun getAllPosts(pageNumber:Int?): Flow<PostResponse> {
        return flow{
            val result = restService.getAllPosts(pageNumber)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}