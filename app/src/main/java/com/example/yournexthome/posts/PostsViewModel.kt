package com.example.yournexthome.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yournexthome.Model.Post

class PostsViewModel:ViewModel() {
    var posts: LiveData<List<Post>>? = null
}