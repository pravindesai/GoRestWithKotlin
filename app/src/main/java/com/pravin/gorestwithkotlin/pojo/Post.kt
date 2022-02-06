package com.pravin.gorestwithkotlin.pojo

import com.google.gson.annotations.SerializedName

data class Post(

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null
)
