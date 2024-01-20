package com.example.yournexthome.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.yournexthome.R
import com.example.yournexthome.posts.Posts

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val signinButton = view.findViewById<Button>(R.id.login_btn)
        signinButton.setOnClickListener(::onLoginButtonClicked)

        val signupLink = view.findViewById<TextView>(R.id.signup_btn)
        signupLink.setOnClickListener(::onSignupLinkClicked)
        return view
    }

    fun onLoginButtonClicked(view: View) {
        val intent = Intent(view.context, Posts::class.java)
        startActivity(intent)
    }

    fun onSignupLinkClicked(view: View) {
        val intent = Intent(view.context, Signup::class.java)
        startActivity(intent)
    }

}