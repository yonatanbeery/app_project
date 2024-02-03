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
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : Fragment() {
    private var usernameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null
    private var errorMessageTextView: TextView? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        super.onCreate(savedInstanceState)

        usernameTextView = view.findViewById(R.id.newUsername)
        emailTextView = view.findViewById(R.id.newEmail)
        passwordTextView = view.findViewById(R.id.newPassword)
        confirmPasswordTextView = view.findViewById(R.id.confirmNewPassword)
        errorMessageTextView = view.findViewById(R.id.errorMessage)

        val registerBtn: Button = view.findViewById(R.id.register_btn)
        registerBtn.setOnClickListener(::onRegisterButtonClicked)

        return view
    }

    fun onRegisterButtonClicked(view: View) {
        val username = usernameTextView?.text.toString()
        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()
        val confirmedPassword = confirmPasswordTextView?.text.toString()

        if (username.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || confirmedPassword.isNullOrBlank()) {
            errorMessageTextView?.text = "Please fill all mandatory values"
        }
        else if (password != confirmedPassword) {
            errorMessageTextView?.text = "Different passwords"
        } else {
            errorMessageTextView?.text = ""
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    Navigation.findNavController(view).navigate(R.id.action_global_loginFragment)
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
}