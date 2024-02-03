package com.example.yournexthome.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = auth.currentUser
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        if (currentUser != null) {
            //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_postsFragment)
        }
        val signInButton = view.findViewById<Button>(R.id.login_btn)
        signInButton.setOnClickListener(::onLoginButtonClicked)

        val signupLink = view.findViewById<TextView>(R.id.signup_btn)
        signupLink.setOnClickListener(::onSignupLinkClicked)
        return view
    }

    fun onLoginButtonClicked(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_postsFragment)
    }

    fun onSignupLinkClicked(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
    }
    //Navigation.findNavController(view/it).popBackStack(R.id.loginFragment, false)
}