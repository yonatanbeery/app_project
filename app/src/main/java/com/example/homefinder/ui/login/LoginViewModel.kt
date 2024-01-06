package com.example.homefinder.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("login page")

    val text: LiveData<String> = _text
}