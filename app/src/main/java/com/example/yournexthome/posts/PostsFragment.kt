package com.example.yournexthome.posts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournexthome.MainActivity
import com.example.yournexthome.Model.City
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post
import com.example.yournexthome.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

class PostsFragment : Fragment() {
    private var postsRecyclerView: RecyclerView? = null
    private var posts: List<Post>? = null
    private var adapter = PostsRecyclerAdapter(posts)
    private var progressBar:ProgressBar? = null

    private lateinit var spinnerCity: SearchableSpinner
    private lateinit var etMinPriceSearch: EditText
    private lateinit var etMaxPriceSearch: EditText
    private lateinit var etBedsSearch: EditText
    private lateinit var etBathsSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var cities: List<City>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = (activity as MainActivity).cities
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE
        super.onCreate(savedInstanceState)

        spinnerCity = view.findViewById(R.id.spinnerCityPosts)
        etMinPriceSearch = view.findViewById(R.id.etMinPriceSearch)
        etMaxPriceSearch = view.findViewById(R.id.etMaxPriceSearch)
        etBedsSearch = view.findViewById(R.id.etBedsSearch)
        etBathsSearch = view.findViewById(R.id.etBathsSearch)
        btnSearch = view.findViewById(R.id.btnSearch)

        setupCityDropdown()
        setupCitySelectionListener()

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
    private fun setupCityDropdown() {
        val blankOption = getString(R.string.blank_option)
        val mutableCityNames = cities.map { it.שם_ישוב_לועזי }.toMutableList()
        mutableCityNames.add(0, blankOption)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableCityNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter
        spinnerCity.setTitle(getString(R.string.select_city))
        spinnerCity.setPositiveButton(getString(R.string.close))
        spinnerCity.setSelection(0)
    }

    private fun setupCitySelectionListener() {
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCity = parent?.getItemAtPosition(position) as String
                    if (selectedCity != getString(R.string.blank_option)) {
                        // Handle selected city=
                        Log.d("PostsFragment", "Selected city: $selectedCity")
                    } else {
                Log.d("PostsFragment", "No city selected")
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.d("PostsFragment", "No city selected")
            }
        }
    }

    private fun getPosts() {
        val city = if ((spinnerCity.selectedItem as String).isNullOrBlank()) {
            null
        } else {
            spinnerCity.selectedItem as String
        }
        val minPrice = etMinPriceSearch.text.toString().toIntOrNull()
        val maxPrice = etMaxPriceSearch.text.toString().toIntOrNull()
        val minBeds = etBedsSearch.text.toString().toIntOrNull()
        val minBaths = etBathsSearch.text.toString().toIntOrNull()

        Model.instance.getFilteredPosts(null, city, minPrice, maxPrice, minBeds, minBaths) { filteredPosts ->
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
