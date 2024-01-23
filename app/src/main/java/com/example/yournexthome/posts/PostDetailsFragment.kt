package com.example.yournexthome.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yournexthome.R

class PostDetailsFragment : Fragment() {
    var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)
        val postDetailsId = arguments?.let {
            PostDetailsFragmentArgs.fromBundle(it).postId
        }
        textView = view.findViewById(R.id.detailText)
        textView?.text = postDetailsId ?: "set something"

        return view
    }
}