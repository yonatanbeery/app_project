package com.example.yournexthome.posts

import androidx.appcompat.app.AppCompatActivity

class PostsRecyclerViewActivity : AppCompatActivity() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}