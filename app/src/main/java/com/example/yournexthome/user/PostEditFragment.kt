package com.example.yournexthome.user

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
import android.widget.Toast
import com.example.yournexthome.MainActivity
import com.example.yournexthome.Model.City
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.example.yournexthome.posts.PostDetailsFragmentArgs
import com.squareup.picasso.Picasso
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.UUID

class PostEditFragment : Fragment() {
    private var post: Post? = null
    private lateinit var citySpinner: SearchableSpinner
    private var priceTextView: TextView? = null
    private var areaSizeTextView: TextView? = null
    private var bedroomsTextView: TextView? = null
    private var bathroomsTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var phoneTextView: TextView? = null
    private var freeTextTextView: TextView? = null
    private var creatorId: String? = null
    private var postId: String? = null
    private var errorMessageTextView: TextView? = null
    private lateinit var cities: List<City>
    private var postPicture: ImageView? = null
    private var imageUri: Uri? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = (activity as MainActivity).cities
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post_edit, container, false)

        citySpinner = view.findViewById(R.id.spinnerCityNewPost)
        priceTextView = view.findViewById(R.id.price)
        areaSizeTextView = view.findViewById(R.id.areaSize)
        bedroomsTextView = view.findViewById(R.id.bedrooms)
        bathroomsTextView = view.findViewById(R.id.bathrooms)
        nameTextView = view.findViewById(R.id.ownerName)
        phoneTextView = view.findViewById(R.id.phone)
        freeTextTextView = view.findViewById(R.id.freeText)
        errorMessageTextView = view.findViewById(R.id.errorMessage)
        postPicture = view.findViewById(R.id.ivPostDetailsImage)
        postPicture!!.setOnClickListener(::setPostPicture)
        postId = arguments?.let { PostDetailsFragmentArgs.fromBundle(it).postId }
        val updateBtn: Button = view.findViewById(R.id.post_btn)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        setupCityDropdown()
        setupCitySelectionListener()

        if (postId != null) {
            setUpPostDetails()
        }

        updateBtn.setOnClickListener(::updatePost)

        return view
    }

    fun setCitySpinnerValue(postCity: String) {
        for (city in cities) {
            if(city.שם_ישוב_לועזי == postCity) {
                citySpinner.setSelection(cities.indexOf(city) + 1)
            }
        }
    }

    fun updatePost(view: View) {
        if (postId != null && citySpinner.selectedItem != null && priceTextView?.text != null && areaSizeTextView?.text != null &&
            bedroomsTextView?.text != null && bathroomsTextView?.text != null && nameTextView?.text != null &&
            phoneTextView?.text != null && freeTextTextView?.text != null && creatorId != null) {
            progressBar?.visibility = View.VISIBLE
            errorMessageTextView?.text = ""
            if (imageUri != null) {
                val randomImageKey = UUID.randomUUID().toString()
                Model.instance.uploadImage("posts", imageUri!!, randomImageKey) {
                    post!!.postPicture = randomImageKey
                    updatePostDetails(view)
                }
            } else {
                updatePostDetails(view)
            }
        } else {
            errorMessageTextView?.text = "Please fill all mandatory values"
        }
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

    fun setUpPostDetails() {
        progressBar?.visibility = View.VISIBLE
        Model.instance.getPost(postId!!) { postResponse ->
            post = postResponse
            setCitySpinnerValue(post?.city.toString())
            priceTextView?.text = post?.price.toString()
            areaSizeTextView?.text = post?.areaSize.toString()
            bedroomsTextView?.text = post?.bedrooms.toString()
            bathroomsTextView?.text = post?.bathrooms.toString()
            nameTextView?.text = post?.name.toString()
            phoneTextView?.text = post?.phone.toString()
            freeTextTextView?.text = post?.freeText.toString()
            creatorId = post?.creatorId.toString()
            Log.i("post", post?.freeText.toString())
            Log.i("post", post?.postPicture.toString())
            Model.instance.getImage("posts", post!!.postPicture) { imageUrl ->
                Picasso.get().load(imageUrl).into(postPicture)
                progressBar?.visibility = View.GONE
            }
        }
    }

    fun updatePostDetails(view: View) {
        val updatedPost = Post(postId!!,
            citySpinner.selectedItem.toString(),
            priceTextView?.text.toString().toInt(),
            areaSizeTextView?.text.toString().toInt(),
            bedroomsTextView?.text.toString().toInt(),
            bathroomsTextView?.text.toString().toInt(),
            nameTextView?.text.toString(),
            phoneTextView?.text.toString(),
            freeTextTextView?.text.toString(),
            creatorId!!,
            post!!.postPicture
        )
        Model.instance.updatePost(updatedPost) {
            showSuccessMessage(view)
            progressBar?.visibility = View.GONE
        }
    }

    fun showSuccessMessage(view: View) {
        errorMessageTextView?.text = ""
        Toast.makeText(
            view.context,
            "updated post successfully.",
            Toast.LENGTH_SHORT,
        ).show()
    }

    fun setPostPicture(view: View) {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode==-1 && data != null && data.data != null) {
            imageUri = data.data
            postPicture?.setImageURI(imageUri)
        }
    }
}