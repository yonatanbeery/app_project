package com.example.yournexthome.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R

class PostsFragment : Fragment() {
    private var postsRecyclerView: RecyclerView? = null
    private var posts: List<Post>? = null
    private var adapter = PostsRecyclerAdapter(posts)
    private var progressBar:ProgressBar? = null

    private lateinit var etCitySearch: EditText
    private lateinit var etMinPriceSearch: EditText
    private lateinit var etMaxPriceSearch: EditText
    private lateinit var etBedsSearch: EditText
    private lateinit var etBathsSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE
        super.onCreate(savedInstanceState)

        etCitySearch = view.findViewById(R.id.etCitySearch)
        etMinPriceSearch = view.findViewById(R.id.etMinPriceSearch)
        etMaxPriceSearch = view.findViewById(R.id.etMaxPriceSearch)
        etBedsSearch = view.findViewById(R.id.etBedsSearch)
        etBathsSearch = view.findViewById(R.id.etBathsSearch)
        btnSearch = view.findViewById(R.id.btnSearch)

        getPosts()

        postsRecyclerView = view.findViewById(R.id.PostsFragmentList)
        postsRecyclerView?.setHasFixedSize(true)
        postsRecyclerView?.layoutManager = LinearLayoutManager(context)

        adapter.listener = object : PostsRecyclerViewActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("Tag", "row $position")
                val post = posts?.get(position)
                post?.let {
                    val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(postId = post.id)
                    Navigation.findNavController(view).navigate(action)
                }

            }
        }
        postsRecyclerView?.adapter = adapter

        btnSearch.setOnClickListener {
            getPosts()
        }

        return view
    }

    private fun getPosts() {
        val city = if (etCitySearch.text.isNullOrBlank()) {
            null
        } else {
            etCitySearch.text.toString()
        }
        val minPrice = etMinPriceSearch.text.toString().toIntOrNull()
        val maxPrice = etMaxPriceSearch.text.toString().toIntOrNull()
        val minBeds = etBedsSearch.text.toString().toIntOrNull()
        val minBaths = etBathsSearch.text.toString().toIntOrNull()

        Model.instance.getFilteredPosts(city, minPrice, maxPrice, minBeds, minBaths) { filteredPosts ->
            this.posts = filteredPosts
            adapter.posts = filteredPosts
            adapter.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        progressBar?.visibility = View.VISIBLE

        getPosts()
    }
}