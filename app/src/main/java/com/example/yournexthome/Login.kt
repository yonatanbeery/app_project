package com.example.yournexthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signinButton = findViewById<Button>(R.id.login_btn)
        signinButton.setOnClickListener(::onLoginButtonClicked)

        val signupLink = findViewById<TextView>(R.id.signup_btn)
        signupLink.setOnClickListener(::onSignupLinkClicked)
    }

    fun onLoginButtonClicked(view: View) {
        val intent = Intent(this, Posts::class.java)
        startActivity(intent)
    }

    fun onSignupLinkClicked(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}