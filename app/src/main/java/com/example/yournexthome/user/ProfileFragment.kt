package com.example.yournexthome.user

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
import androidx.core.net.toUri
import androidx.navigation.Navigation
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.User
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.squareup.picasso.Picasso;
import java.util.UUID


class ProfileFragment : Fragment() {
    private var profileImage: ImageView? = null
    private var imageUri: Uri? = null

    private var userProfile: User? = null
    private var usernameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null
    private var errorMessageTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    val firebaseUser = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileImage = view.findViewById(R.id.userImage)
        profileImage!!.setOnClickListener(::setProfilePicture)

        progressBar = view.findViewById(R.id.progressBar)
        initProfileValues(view)
        val registerBtn: Button = view.findViewById(R.id.update_btn)
        registerBtn.setOnClickListener(::onUpdateUserButtonClicked)

        return view
    }

    fun initProfileValues(view: View) {
        progressBar?.visibility = View.VISIBLE
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
                Model.instance.getImage("users", user!!.profilePicture) {imageUrl ->
                    Picasso.get().load(imageUrl).into(profileImage)
                    progressBar?.visibility = View.GONE
                }
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
            if (imageUri != null) {
                progressBar?.visibility = View.VISIBLE
                val randomImageKey = UUID.randomUUID().toString()
                Model.instance.uploadImage("users", imageUri!!, randomImageKey) {
                    user.username = usernameTextView?.text.toString()
                    user.profilePicture = randomImageKey
                    Model.instance.updateUser(user) {
                        progressBar?.visibility = View.GONE
                        showSuccessMessage(view)
                    }
                }
            }
            else if (usernameTextView?.text?.toString() != user.username) {
                progressBar?.visibility = View.VISIBLE
                user.username = usernameTextView?.text.toString()
                Model.instance.updateUser(user) {
                    progressBar?.visibility = View.GONE
                    showSuccessMessage(view)
                }
            }
            if (passwordTextView?.text.toString() != "####") {
                progressBar?.visibility = View.VISIBLE
                firebaseUser!!.updatePassword(passwordTextView?.text.toString())
                    .addOnCompleteListener{
                        task ->
                        progressBar?.visibility = View.GONE
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