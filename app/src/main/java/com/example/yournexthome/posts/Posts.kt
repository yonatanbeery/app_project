package com.example.yournexthome.posts

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Posts : AppCompatActivity() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}