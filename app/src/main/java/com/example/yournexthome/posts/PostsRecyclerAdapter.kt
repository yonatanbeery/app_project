package com.example.yournexthome.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class PostsRecyclerAdapter(var posts: List<Post>?): RecyclerView.Adapter<PostViewHolder>() {
    var listener: PostsRecyclerViewActivity.OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_list_row,parent,false), listener, posts)
    }

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts?.get(position)
        holder.bind(post, position)
    }

}