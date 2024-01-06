package com.example.homefinder.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("posts page")

    val text: LiveData<String> = _text
}