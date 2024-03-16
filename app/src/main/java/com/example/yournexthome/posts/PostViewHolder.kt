package com.example.yournexthome.posts

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.squareup.picasso.Picasso

class PostViewHolder(itemView: View, listener: PostsRecyclerViewActivity.OnItemClickListener?, posts: List<Post>?): RecyclerView.ViewHolder(itemView){

    var postHeaderText: TextView? = null
    var postDescription: TextView?= null
    var postPicture: ImageView? = null
    private var progressBar: ProgressBar? = null
    init {
        postHeaderText = itemView.findViewById(R.id.postHeader)
        postDescription= itemView.findViewById(R.id.postDescription)
        postPicture = itemView.findViewById(R.id.postImage)
        progressBar = itemView.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE

        itemView.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(post: Post?, position: Int) {
        progressBar?.visibility = View.VISIBLE
        postHeaderText?.text = post?.name
        postDescription?.text = post?.city
        Model.instance.getImage("posts", post!!.postPicture) { imageUrl ->
            Picasso.get().load(imageUrl).into(postPicture)
            progressBar?.visibility = View.GONE
        }
    }
}