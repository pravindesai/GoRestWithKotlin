package com.pravin.gorestwithkotlin.pojo

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("data")
	val posts: MutableList<Post>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)






