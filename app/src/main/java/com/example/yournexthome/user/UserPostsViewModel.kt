package com.example.yournexthome.user

import androidx.lifecycle.ViewModel
import com.example.yournexthome.Model.Post

class UserPostsViewModel:ViewModel() {
    var posts: List<Post>? = null
}