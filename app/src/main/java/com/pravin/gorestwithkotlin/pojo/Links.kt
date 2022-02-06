package com.pravin.gorestwithkotlin.pojo

import com.google.gson.annotations.SerializedName

data class Links(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("current")
    val current: String? = null,

    @field:SerializedName("previous")
    val previous: Any? = null
)
