package com.example.yournexthome.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.yournexthome.BlueFragment
import com.example.yournexthome.R
import com.example.yournexthome.posts.PostsFragment

class Login : AppCompatActivity() {
    var blueFragment: PostsFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signinButton = findViewById<Button>(R.id.login_btn)
        signinButton.setOnClickListener(::onLoginButtonClicked)

        val signupLink = findViewById<TextView>(R.id.signup_btn)
        signupLink.setOnClickListener(::onSignupLinkClicked)

    }

    fun onLoginButtonClicked(view: View) {
//        val intent = Intent(this, Posts::class.java)
//        startActivity(intent)
        if (blueFragment == null) displayBottomFragment()
        else removeBottomFragment()
    }

    fun onSignupLinkClicked(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }

    fun displayBottomFragment() {
        blueFragment = PostsFragment()
        blueFragment?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.bottomFragment, it)
            transaction.addToBackStack("TAG")
            transaction.commit()
        }
    }

    fun removeBottomFragment() {
        blueFragment?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(it)
            transaction.addToBackStack("TAG")
            transaction.commit()
        }
        blueFragment = null
    }
}