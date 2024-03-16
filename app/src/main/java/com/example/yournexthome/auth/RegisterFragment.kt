package com.example.yournexthome.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.yournexthome.MainActivity
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.Model.User
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.UUID

class RegisterFragment : Fragment() {
    private var profileImage: ImageView? = null
    private var imageUri:Uri? = null
    private var usernameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null
    private var errorMessageTextView: TextView? = null
    private var progressBar: ProgressBar? = null

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

        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        val registerBtn: Button = view.findViewById(R.id.register_btn)
        registerBtn.setOnClickListener(::onRegisterButtonClicked)

        profileImage = view.findViewById(R.id.newUserImage)
        profileImage!!.setOnClickListener(::setProfilePicture)

        return view
    }

    fun onRegisterButtonClicked(view: View) {
        val username = usernameTextView?.text.toString()
        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()
        val confirmedPassword = confirmPasswordTextView?.text.toString()

        if (imageUri == null || username.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || confirmedPassword.isNullOrBlank()) {
            errorMessageTextView?.text = "Please fill all mandatory values"
        }
        else if (password != confirmedPassword) {
            errorMessageTextView?.text = "Different passwords"
        } else {
            progressBar?.visibility = View.VISIBLE
            errorMessageTextView?.text = ""
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    if (imageUri != null) {
                        val randomImageKey = UUID.randomUUID().toString()
                        Model.instance.uploadImage("users", imageUri!!, randomImageKey) {
                            val user = User(auth.uid.toString(),email, username, randomImageKey)
                            Model.instance.addUser(user) {
                                Navigation.findNavController(view).navigate(R.id.action_global_loginFragment)
                                progressBar?.visibility = View.GONE
                            }
                        }
                    } else {
                        val user = User(auth.uid.toString(),email, username, "")
                        Model.instance.addUser(user) {
                            Navigation.findNavController(view).navigate(R.id.action_global_loginFragment)
                            progressBar?.visibility = View.GONE
                        }
                    }
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        view.context,
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    progressBar?.visibility = View.GONE
                }
            }
        }
    }

    fun setProfilePicture(view: View) {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
    }

    @Override
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("res1", requestCode.toString())
        Log.i("res2", resultCode.toString())
        Log.i("res3", data.toString())
        if (requestCode==1 && resultCode==-1 && data != null && data.data != null) {
            imageUri = data.data
            profileImage?.setImageURI(imageUri)
        }
    }
}