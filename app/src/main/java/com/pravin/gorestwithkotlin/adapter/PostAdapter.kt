package com.pravin.gorestwithkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pravin.gorestwithkotlin.R
import com.pravin.gorestwithkotlin.databinding.PostLayoutBinding
import com.pravin.gorestwithkotlin.pojo.Post

class PostAdapter(val postlist: List<Post>):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val TAG = "**" + this.javaClass.simpleName

    inner class PostViewHolder(view:View):RecyclerView.ViewHolder(view){
        val postId:TextView   = view.findViewById(R.id.postId)
        val userId:TextView   = view.findViewById(R.id.userId)
        val titleTv:TextView  = view.findViewById(R.id.titleTv)
        val bodyTv:TextView   = view.findViewById(R.id.bodyTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,null, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postlist.get(position)
        holder.postId.text = post.id.toString()
        holder.userId.text = post.userId.toString()
        holder.titleTv.text = post.title
        holder.bodyTv.text = post.body
    }

    override fun getItemCount(): Int = postlist.size

}