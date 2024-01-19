package com.example.yournexthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post

class Posts : AppCompatActivity() {
    private var postsRecyclerView: RecyclerView? = null
    private var posts: MutableList<Post>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_list)

        posts = Model.instance.posts

        postsRecyclerView = findViewById(R.id.postsList)
        postsRecyclerView?.setHasFixedSize(true)
        postsRecyclerView?.layoutManager = LinearLayoutManager(this)

        val adapter = PostsRecyclerAdapter()
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("Tag", "row $position")
            }
        }
        postsRecyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class PostViewHolder(itemView: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView){

        var postHeaderText: TextView? = null
        var postDescription: TextView?= null
        var postCheckBox: CheckBox?= null
        init {
             postHeaderText = itemView.findViewById(R.id.postHeader)
             postDescription= itemView.findViewById(R.id.postDescription)
             postCheckBox = itemView.findViewById(R.id.postRowCheckbox)

            postCheckBox!!.setOnClickListener {
                (postCheckBox!!.tag as? Int)?.let { tag ->
                    posts?.get(tag)?.isChecked = postCheckBox!!.isChecked
                }
            }
            itemView.setOnClickListener {
                Log.i("Tag", "row adapter $adapterPosition")
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bind(post: Post?, position: Int) {
            postHeaderText?.text = post?.header
            postDescription?.text = post?.description
            postCheckBox?.apply {
                isChecked = post?.isChecked ?: false
                tag = position
            }
        }
    }
    inner class PostsRecyclerAdapter: RecyclerView.Adapter<PostViewHolder>() {
        var listener:OnItemClickListener? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            Log.i("JOHN", "$posts")
            return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_list_row,parent,false), listener)
        }

        override fun getItemCount(): Int = posts?.size ?: 0

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post = posts?.get(position)
            holder.bind(post, position)
        }

    }
}