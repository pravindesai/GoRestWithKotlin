package com.pravin.gorestwithkotlin.pojo

import com.google.gson.annotations.SerializedName

data class Pagination(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("pages")
    val pages: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @field:SerializedName("page")
    val page: Int? = null
)
