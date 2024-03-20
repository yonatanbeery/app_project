package com.example.yournexthome.posts

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.squareup.picasso.Picasso

class PostViewHolder(itemView: View, listener: PostsRecyclerViewActivity.OnItemClickListener?, posts: List<Post>?): RecyclerView.ViewHolder(itemView){

    var postPicture: ImageView? = null
    var postDetails: TextView? = null
    var postPrice: TextView? = null
    var postCity: TextView? = null
    private var progressBar: ProgressBar? = null
    init {
        postPicture = itemView.findViewById(R.id.postCardImage)
        postDetails = itemView.findViewById(R.id.postCardDetails)
        postPrice = itemView.findViewById(R.id.postCardPrice)
        postCity = itemView.findViewById(R.id.postCardCity)
        progressBar = itemView.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        itemView.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(post: Post?, position: Int) {
        progressBar?.visibility = View.VISIBLE
        postDetails?.text = "${post?.bedrooms} beds | ${post?.bathrooms} baths | ${post?.areaSize} sqft"
        postCity?.text = post?.city
        postPrice?.text = "${post?.price.toString()} $"
        Model.instance.getImage("posts", post!!.postPicture) { imageUrl ->
            Picasso.get().load(imageUrl).into(postPicture)
            progressBar?.visibility = View.GONE
        }
    }
}