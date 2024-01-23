package com.example.yournexthome.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class PostsFragment : Fragment() {
    private var postsRecyclerView: RecyclerView? = null
    private var posts: MutableList<Post>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        super.onCreate(savedInstanceState)

        posts = Model.instance.posts

        postsRecyclerView = view.findViewById(R.id.PostsFragmentList)
        postsRecyclerView?.setHasFixedSize(true)
        postsRecyclerView?.layoutManager = LinearLayoutManager(context)

        val adapter = PostsRecyclerAdapter(posts)
        adapter.listener = object : Posts.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("Tag", "row $position")
                val post = posts?.get(position)
                post.let {
                    val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(postId = position.toString())
                    Navigation.findNavController(view).navigate(action)
                }

            }
        }
        postsRecyclerView?.adapter = adapter
        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}