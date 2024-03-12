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
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.User
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth

class ProfileFragment : Fragment() {
    private var userProfile: User? = null
    private var usernameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null
    private var errorMessageTextView: TextView? = null
    val firebaseUser = Firebase.auth.currentUser

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

        emailTextView?.text = firebaseUser?.email
        passwordTextView?.text = "####"
        confirmPasswordTextView?.text = "####"

        try {
            Model.instance.getUserDetails(firebaseUser?.email ?: "") { user ->
                userProfile = user
                usernameTextView?.text = user?.username
            }
        } catch (e: Throwable) {
            errorMessageTextView?.text = "לא ניתן לשלוף פרטי משתמש"
        }
    }

    fun onUpdateUserButtonClicked(view: View) {
        val user = userProfile
        if (passwordTextView?.text.toString() != confirmPasswordTextView?.text.toString()) {
            errorMessageTextView?.text = "passwords mismatched"
        }
        else if (user != null) {
            if (usernameTextView?.text?.toString() != user.username) {
                user.username = usernameTextView?.text.toString()
                Model.instance.updateUser(user) {
                    showSuccessMessage(view)
                }
                Log.i("TAG", "updated user username / picture")
            }
            if (passwordTextView?.text.toString() != "####") {
                firebaseUser!!.updatePassword(passwordTextView?.text.toString())
                    .addOnCompleteListener{
                        task ->
                    if (task.isSuccessful) {
                        showSuccessMessage(view)
                    } else {
                        Toast.makeText(
                            view.context,
                            task.exception?.message,
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
    }

    fun showSuccessMessage(view: View) {
        errorMessageTextView?.text = ""
        Toast.makeText(
            view.context,
            "updated details successfully.",
            Toast.LENGTH_SHORT,
        ).show()
    }
}