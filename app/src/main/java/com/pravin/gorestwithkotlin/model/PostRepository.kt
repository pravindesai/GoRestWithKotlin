package com.pravin.gorestwithkotlin.model

import com.pravin.gorestwithkotlin.pojo.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostRepository {
    private val BASE_URL = "https://gorest.co.in/public/v1/"
    private val restService: GoRestService
    private val retrofit:Retrofit
    init {
        retrofit   = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        restService = retrofit.create(GoRestService::class.java)
    }

    fun getAllPosts(pageNumber:Int?): Flow<PostResponse> {
        return flow{
            val result = restService.getAllPosts(pageNumber)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}