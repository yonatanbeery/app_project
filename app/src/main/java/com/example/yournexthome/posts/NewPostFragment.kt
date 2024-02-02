package com.example.yournexthome.posts

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
 * Use the [NewPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewPostFragment : Fragment() {
    private var cityTextView: TextView? = null
    private var priceTextView: TextView? = null
    private var areaSizeTextView: TextView? = null
    private var bedroomsTextView: TextView? = null
    private var bathroomsTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var phoneTextView: TextView? = null
    private var freeTextTextView: TextView? = null
    private var errorMessageTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_post, container, false)
        super.onCreate(savedInstanceState)

        cityTextView = view.findViewById(R.id.city)
        priceTextView = view.findViewById(R.id.price)
        areaSizeTextView = view.findViewById(R.id.areaSize)
        bedroomsTextView = view.findViewById(R.id.bedrooms)
        bathroomsTextView = view.findViewById(R.id.bathrooms)
        nameTextView = view.findViewById(R.id.ownerName)
        phoneTextView = view.findViewById(R.id.phone)
        freeTextTextView = view.findViewById(R.id.freeText)
        errorMessageTextView = view.findViewById(R.id.errorMessage)

        val registerBtn: Button = view.findViewById(R.id.post_btn)

        registerBtn.setOnClickListener {
            val city = cityTextView?.text.toString()
            val price = priceTextView?.text.toString()
            val areaSize = areaSizeTextView?.text.toString()
            val bedrooms = bedroomsTextView?.text.toString()
            val bathrooms = bathroomsTextView?.text.toString()
            val name = nameTextView?.text.toString()
            val phone = phoneTextView?.text.toString()
            val freeText = freeTextTextView?.text.toString()

            if(city == "" || price == "" || areaSize == "" || bedrooms == "" || bathrooms == "" || name == "" || phone == "") {
                errorMessageTextView?.text = "Please fill all mandatory values"
            } else {
                errorMessageTextView?.text = ""
                val post = Post(city, price, areaSize, "", false)
                Model.instance.addPost(post) {
                    Navigation.findNavController(view).popBackStack(R.id.loginFragment, false)
                }
            }
        }

        return view
    }
}