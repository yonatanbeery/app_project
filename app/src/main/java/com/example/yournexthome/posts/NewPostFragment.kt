package com.example.yournexthome.posts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.yournexthome.MainActivity
import com.example.yournexthome.Model.City
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.UUID

class NewPostFragment : Fragment() {
    val firebaseUser = Firebase.auth.currentUser
    private lateinit var citySpinner: SearchableSpinner
    private var priceTextView: TextView? = null
    private var areaSizeTextView: TextView? = null
    private var bedroomsTextView: TextView? = null
    private var bathroomsTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var phoneTextView: TextView? = null
    private var freeTextTextView: TextView? = null
    private var errorMessageTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var cities: List<City>
    private var postImage: ImageView? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = (activity as MainActivity).cities
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_post, container, false)
        super.onCreate(savedInstanceState)

        citySpinner = view.findViewById(R.id.spinnerCityNewPost)
        priceTextView = view.findViewById(R.id.price)
        areaSizeTextView = view.findViewById(R.id.areaSize)
        bedroomsTextView = view.findViewById(R.id.bedrooms)
        bathroomsTextView = view.findViewById(R.id.bathrooms)
        nameTextView = view.findViewById(R.id.ownerName)
        phoneTextView = view.findViewById(R.id.phone)
        freeTextTextView = view.findViewById(R.id.freeText)
        errorMessageTextView = view.findViewById(R.id.errorMessage)

        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        postImage = view.findViewById(R.id.ivPostDetailsImage)
        postImage!!.setOnClickListener(::setPostImage)

        setupCityDropdown()
        setupCitySelectionListener()

        val registerBtn: Button = view.findViewById(R.id.post_btn)

        registerBtn.setOnClickListener {
            val city = if ((citySpinner.selectedItem as String).isNullOrBlank()) {
                null
            } else {
                citySpinner.selectedItem as String
            }
            val price = priceTextView?.text.toString()
            val areaSize = areaSizeTextView?.text.toString()
            val bedrooms = bedroomsTextView?.text.toString()
            val bathrooms = bathroomsTextView?.text.toString()
            val name = nameTextView?.text.toString()
            val phone = phoneTextView?.text.toString()
            val freeText = freeTextTextView?.text.toString()

            if(imageUri == null || city.isNullOrBlank() || price.isNullOrBlank() || areaSize.isNullOrBlank() || bedrooms.isNullOrBlank() || bathrooms.isNullOrBlank() || name.isNullOrBlank() || phone.isNullOrBlank()) {
                errorMessageTextView?.text = "Please fill all mandatory values"
            } else {
                progressBar?.visibility = View.VISIBLE
                errorMessageTextView?.text = ""
                val randomImageKey = UUID.randomUUID().toString()
                Model.instance.uploadImage("posts", imageUri!!, randomImageKey) {
                    val post = Post("", city, price.toInt(), areaSize.toInt(), bedrooms.toInt(), bathrooms.toInt(), name, phone, freeText, firebaseUser!!.uid, randomImageKey)
                    Model.instance.addPost(post) {
                        Navigation.findNavController(view).navigate(R.id.action_global_postsFragment)
                        progressBar?.visibility = View.GONE
                    }
                }
            }
        }

        return view
    }

    private fun setupCityDropdown() {
        val blankOption = getString(R.string.blank_option)
        val mutableCityNames = cities.map { it.שם_ישוב_לועזי }.toMutableList()
        mutableCityNames.add(0, blankOption)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableCityNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter
        citySpinner.setTitle(getString(R.string.select_city))
        citySpinner.setPositiveButton(getString(R.string.close))
        citySpinner.setSelection(0)
    }

    private fun setupCitySelectionListener() {
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = parent?.getItemAtPosition(position) as String
                if (selectedCity != getString(R.string.blank_option)) {
                    Log.d("NewPostFragment", "Selected city: $selectedCity")
                } else {
                    Log.d("NewPostFragment", "No city selected")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("NewPostFragment", "No city selected")
            }
        }
    }

    fun setPostImage(view: View) {
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
            postImage?.setImageURI(imageUri)
        }
    }
}