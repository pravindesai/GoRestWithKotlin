package com.pravin.gorestwithkotlin.model

import com.pravin.gorestwithkotlin.pojo.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoRestService {
    @GET("posts")
    suspend fun getAllPosts(@Query("page") pageNumber:Int?):PostResponse
}