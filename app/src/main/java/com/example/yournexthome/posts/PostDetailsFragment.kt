package com.example.yournexthome.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.squareup.picasso.Picasso

class PostDetailsFragment : Fragment() {
    var postPrice: TextView? = null
    var postDescription: TextView?= null
    var postFreeText: TextView?= null
    var postOwnerName: TextView?= null
    var postOwnerPhoneNumber: TextView?= null
    var postPicture: ImageView? = null
    private var progressBar: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)

        postPrice = view.findViewById<TextView>(R.id.tvPostDetailsPrice)
        postDescription = view.findViewById<TextView>(R.id.tvPostDetailsDescription)
        postFreeText = view.findViewById<TextView>(R.id.tvPostDetailsFreeText)
        postOwnerName = view.findViewById<TextView>(R.id.tvPostDetailsName)
        postOwnerPhoneNumber = view.findViewById<TextView>(R.id.tvPostDetailsPhoneNumber)
        postPicture = view.findViewById(R.id.ivPostDetailsImage)

        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        val postId = arguments?.let { PostDetailsFragmentArgs.fromBundle(it).postId }

        if (postId != null) {
            progressBar?.visibility = View.VISIBLE
            Model.instance.getPost(postId) { post ->
                postPrice?.text = "${post?.price.toString()} $"
                postDescription?.text = "${post?.city} | ${post?.bedrooms} beds | ${post?.bathrooms} baths | ${post?.areaSize} sqft"
                postFreeText?.text = post?.freeText
                postOwnerName?.text = post?.name
                postOwnerPhoneNumber?.text = post?.phone
                Model.instance.getImage("posts", post!!.postPicture) { imageUrl ->
                    Picasso.get().load(imageUrl).into(postPicture)
                    progressBar?.visibility = View.GONE
                }
            }
        }

        return view
    }
}
