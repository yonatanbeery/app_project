package com.example.yournexthome.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.yournexthome.posts.Posts
import com.example.yournexthome.R

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val registerButton = findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener(::onRegisterButtonClicked)
    }

    fun onRegisterButtonClicked(view: View) {
        val intent = Intent(this, Posts::class.java)
        startActivity(intent)
    }
}