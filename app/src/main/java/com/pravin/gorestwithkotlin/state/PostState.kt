package com.pravin.gorestwithkotlin.state

import com.pravin.gorestwithkotlin.pojo.PostResponse

sealed interface PostState{

    object Empty: PostState
    object Loading: PostState
    data class Error(val msg:String): PostState
    data class Failure(var msg:String): PostState
    data class Sucess(val postResponse: PostResponse): PostState
}