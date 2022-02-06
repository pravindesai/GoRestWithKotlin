package com.pravin.gorestwithkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.pravin.gorestwithkotlin.adapter.PostAdapter
import com.pravin.gorestwithkotlin.state.PostState
import com.pravin.gorestwithkotlin.databinding.GorestActivityMainBinding
import com.pravin.gorestwithkotlin.pojo.Post
import com.pravin.gorestwithkotlin.pojo.PostResponse
import com.pravin.gorestwithkotlin.viewmodel.GoRestActivityViewModel
import kotlinx.coroutines.flow.collect

class GoRestActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "**" + this.javaClass.simpleName

    lateinit var viewModel: GoRestActivityViewModel
    lateinit var binding: GorestActivityMainBinding

    lateinit var postResponse: PostResponse
    lateinit var postAdapter: PostAdapter

    var postsList: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GorestActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GoRestActivityViewModel::class.java)
        binding.next.setOnClickListener(this)
        binding.prev.setOnClickListener(this)

        postAdapter = PostAdapter(postsList)
        binding.postRv.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@GoRestActivity, RecyclerView.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()
        getFcmToken()
        viewModel.getAllPosts(viewModel.pageNumber)
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful){
                Log.i("**FCM TOKEN", "FcmToken: "+it.result )
            }
        }
    }

    //next prev button clicked
    override fun onClick(v: View?) {
        when (v) {
            binding.next -> {
                viewModel.getAllPosts(viewModel.pageNumber?.plus(1))
            }
            binding.prev -> {
                viewModel.getAllPosts(viewModel.pageNumber?.minus(1))
            }
        }
    }

    val getPostsJob = lifecycleScope.launchWhenStarted {
        viewModel._postStateFlow.collect {
            when (it) {
                is PostState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is PostState.Failure -> {
                    Log.e(TAG, "Request Failed "+it.msg )
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, "Request Failed \nPlease Check Error logs", Snackbar.LENGTH_SHORT).show()
                }
                is PostState.Sucess -> {
                    binding.progressBar.visibility = View.GONE
                    postResponse = it.postResponse
                    viewModel.pageNumber = postResponse.meta?.pagination?.page
                    postResponse.posts?.let {
                        postsList.apply {
                            clear()
                            addAll(it)
                            postAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }
        }
    }

}