package com.example.yournexthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.example.yournexthome.Model.Model
import com.example.yournexthome.Model.Post

class Posts : AppCompatActivity() {
    private var postsListView: ListView? = null
    private var posts: MutableList<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts)

        posts = Model.instance.posts

        postsListView = findViewById(R.id.postsList)
        postsListView?.adapter = PostsListAdapter(posts)

        postsListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("Tag","Row $position was clicked" )
        }
    }

    class PostsListAdapter(val posts: MutableList<Post>?): BaseAdapter() {
        override fun getCount(): Int = posts?.size ?: 0

        override fun getItem(p0: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val post = posts?.get(position)
            var view: View? = null
            if (convertView == null){
                view = LayoutInflater.from(parent?.context).inflate(R.layout.post_list_row,parent,false)
                val postCheckBox:CheckBox? = view?.findViewById(R.id.postRowCheckbox)
                postCheckBox?.setOnClickListener {
                    (postCheckBox.tag as? Int)?.let { tag ->
                        posts?.get(tag)?.isChecked = postCheckBox.isChecked
                    }
                }
            }

            view = view ?: convertView
            val postHeaderText: TextView? = view?.findViewById(R.id.postHeader)
            val postDescription: TextView? = view?.findViewById(R.id.postDescription)
            val postCheckBox: CheckBox? = view?.findViewById(R.id.postRowCheckbox)
            postHeaderText?.text = post?.header
            postDescription?.text = post?.description
            postCheckBox?.apply {
                isChecked = post?.isChecked ?: false
                    tag = position
            }

            return view!!
        }

        override fun getItemId(p0: Int): Long {
            TODO("Not yet implemented")
        }
    }
}