package com.example.yournexthome.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private var usernameTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var confirmPasswordTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        super.onCreate(savedInstanceState)

        usernameTextView = view.findViewById(R.id.newUsername)
        passwordTextView = view.findViewById(R.id.newPassword)
        confirmPasswordTextView = view.findViewById(R.id.confirmNewPassword)
        val registerBtn: Button = view.findViewById(R.id.register_btn)

        registerBtn.setOnClickListener {
            val username = usernameTextView?.text.toString()
            val password = passwordTextView?.text.toString()
            val confirmedPassword = confirmPasswordTextView?.text.toString()
            /*if (password == confirmedPassword) {
                val post = Post(username, username, password, "", false)
                Model.instance.addPost(post) {
                    Navigation.findNavController(view).popBackStack(R.id.loginFragment, false)
                }
            }*/
        }

        return view
    }
}