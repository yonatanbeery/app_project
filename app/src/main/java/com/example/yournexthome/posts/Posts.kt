package com.example.yournexthome.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class Posts : AppCompatActivity() {
    private var postsRecyclerView: RecyclerView? = null
    private var posts: MutableList<Post>? = null
    /*override fun onCreate(savedInstanceState: Bundle?) {

    }*/

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}