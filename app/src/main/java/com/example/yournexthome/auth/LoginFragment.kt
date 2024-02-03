package com.example.yournexthome.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailTextView = view.findViewById(R.id.sgnEmail)
        passwordTextView = view.findViewById(R.id.sgnPassword)

        val signInButton = view.findViewById<Button>(R.id.login_btn)
        signInButton.setOnClickListener(::onLoginButtonClicked)

        val signupLink = view.findViewById<TextView>(R.id.signup_btn)
        signupLink.setOnClickListener(::onSignupLinkClicked)
        return view
    }

    fun onLoginButtonClicked(view: View) {
        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()

        if(!(email.isNullOrBlank() || password.isNullOrBlank())) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_postsFragment)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        view.context,
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    fun onSignupLinkClicked(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
    }
}