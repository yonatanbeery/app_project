package com.example.homefinder.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("signup page")

    val text: LiveData<String> = _text
}