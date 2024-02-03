package com.example.yournexthome.user

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
import com.google.firebase.auth.auth

class ProfileFragment : Fragment() {
    private var usernameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null
    private var errorMessageTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initProfileValues(view)
        val registerBtn: Button = view.findViewById(R.id.update_btn)
        registerBtn.setOnClickListener(::onUpdateUserButtonClicked)
        return view
    }

    fun initProfileValues(view: View) {
        usernameTextView = view.findViewById(R.id.newUsername)
        emailTextView = view.findViewById(R.id.newEmail)
        passwordTextView = view.findViewById(R.id.newPassword)
        confirmPasswordTextView = view.findViewById(R.id.confirmNewPassword)
        errorMessageTextView = view.findViewById(R.id.errorMessage)

        val user = Firebase.auth.currentUser
        usernameTextView?.text = user?.email
    }

    fun onUpdateUserButtonClicked(view: View) {
        //todo update user settings
    }
}