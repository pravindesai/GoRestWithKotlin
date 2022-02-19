package com.pravin.gorestwithkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pravin.gorestwithkotlin.model.PostRepository
import com.pravin.gorestwithkotlin.state.PostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoRestActivityViewModel @Inject constructor():ViewModel() {
    private val postRepo: PostRepository = PostRepository()

    private val postStateFlow: MutableStateFlow<PostState> = MutableStateFlow(PostState.Empty)
    val _postStateFlow:StateFlow<PostState> = postStateFlow

    var pageNumber: Int? = null

    fun getAllPosts(pageNumber:Int?) = viewModelScope.launch(Dispatchers.IO) {
        postStateFlow.value = PostState.Loading
        postRepo.getAllPosts(pageNumber).catch {e->
            postStateFlow.value = PostState.Failure(e.message.toString())
        }.collect {postResponse->
            postStateFlow.value = PostState.Sucess(postResponse)
        }
    }


}