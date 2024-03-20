package com.example.yournexthome.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.yournexthome.Model.Model
import com.example.yournexthome.R
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class PostDetailsFragment : DialogFragment() {
    var postPrice: TextView? = null
    var postCity: TextView?= null
    var postDescription: TextView?= null
    var postFreeText: TextView?= null
    var postOwnerName: TextView?= null
    var postOwnerPhoneNumber: TextView?= null
    var postPicture: ImageView? = null
    private var progressBar: ProgressBar? = null
    var btnExitDetailsDialog: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)

        postPrice = view.findViewById<TextView>(R.id.tvPostDetailsPrice)
        postCity = view.findViewById<TextView>(R.id.tvPostDetailsCity)
        postDescription = view.findViewById<TextView>(R.id.tvPostDetailsDescription)
        postFreeText = view.findViewById<TextView>(R.id.tvPostDetailsFreeText)
        postOwnerName = view.findViewById<TextView>(R.id.tvPostDetailsName)
        postOwnerPhoneNumber = view.findViewById<TextView>(R.id.tvPostDetailsPhoneNumber)
        postPicture = view.findViewById(R.id.ivPostDetailsImage)

        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE
        btnExitDetailsDialog = view.findViewById(R.id.btnExitDetailsDialog)
        btnExitDetailsDialog?.setOnClickListener {
            dismiss()
        }

        val postId = arguments?.let { PostDetailsFragmentArgs.fromBundle(it).postId }

        if (postId != null) {
            progressBar?.visibility = View.VISIBLE
            Model.instance.getPost(postId) { post ->
                postPrice?.text = "${post?.price.toString()} $"
                postCity?.text = post?.city
                postDescription?.text = "${post?.bedrooms} beds | ${post?.bathrooms} baths | ${post?.areaSize} sqft"
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

    companion object {
        fun newInstance(postId: String): PostDetailsFragment {
            val fragment = PostDetailsFragment()
            val args = Bundle()
            args.putString("postId", postId)
            fragment.arguments = args
            return fragment
        }
    }
}
