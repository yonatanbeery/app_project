package com.example.yournexthome.posts

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class PostViewHolder(itemView: View, listener: Posts.OnItemClickListener?, posts: MutableList<Post>?): RecyclerView.ViewHolder(itemView){

    var postHeaderText: TextView? = null
    var postDescription: TextView?= null
    var postCheckBox: CheckBox?= null
    var post: Post? = null
    init {
        postHeaderText = itemView.findViewById(R.id.postHeader)
        postDescription= itemView.findViewById(R.id.postDescription)
        postCheckBox = itemView.findViewById(R.id.postRowCheckbox)

        postCheckBox!!.setOnClickListener {
            val post = posts?.get(adapterPosition)
            post?.isChecked = postCheckBox?.isChecked ?: false
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