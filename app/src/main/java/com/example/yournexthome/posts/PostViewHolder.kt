package com.example.yournexthome.posts

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class PostViewHolder(itemView: View, listener: PostsRecyclerViewActivity.OnItemClickListener?, posts: List<Post>?): RecyclerView.ViewHolder(itemView){

    var postHeaderText: TextView? = null
    var postDescription: TextView?= null
    var postCheckBox: CheckBox?= null
    init {
        postHeaderText = itemView.findViewById(R.id.postHeader)
        postDescription= itemView.findViewById(R.id.postDescription)
        postCheckBox = itemView.findViewById(R.id.postRowCheckbox)

        itemView.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(post: Post?, position: Int) {
        postHeaderText?.text = post?.name
        postDescription?.text = post?.city
    }
}