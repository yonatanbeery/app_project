package com.example.yournexthome.Model

import android.net.Uri
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.example.yournexthome.dao.AppLocalDB
import java.util.concurrent.Executors

class Model private constructor(){
    private var database = AppLocalDB.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var firebaseModel = FirebaseModel()

    companion object {
        val instance: Model = Model()
    }

    fun addPost(post: Post, callback: ()-> Unit) {
        firebaseModel.addPost(post, callback)
    }

    fun updatePost(post: Post, callback: ()-> Unit) {
        firebaseModel.updatePost(post, callback)
    }

    fun getPost(postId: String, callback: (Post?)-> Unit) {
        firebaseModel.getPost(postId, callback)
    }

    fun getFilteredPosts(creatorId: String?, city: String?, minPrice: Int?, maxPrice: Int?, minBeds: Int?, minBaths: Int?, callback: (List<Post>)-> Unit) {
        var lastUpdated: Long = Post.lastUpdated

        firebaseModel.getFilteredPosts(lastUpdated, creatorId, city, minPrice, maxPrice, minBeds, minBaths) {list ->
            executor.execute {
                var time = lastUpdated
                for(post in list) {
                    database.PostDao().insert(post)
                    post.lastUpdated?.let {
                        if(time < it) time = post.lastUpdated ?: System.currentTimeMillis()
                    }
                }
                Post.lastUpdated = time
                val posts = database.PostDao().gatFilteredPosts(creatorId, city, minPrice, maxPrice, minBeds, minBaths)
                mainHandler.post{
                    callback(posts)
                }
            }
        }
    }

    fun addUser(user: User, callback: ()-> Unit) {
        firebaseModel.addUser(user, callback)
    }

    fun getUserDetails(email: String, callback: (User?)-> Unit) {
        firebaseModel.getUser(email, callback)
    }

    fun updateUser(user: User, callback: ()-> Unit) {
        firebaseModel.updateUser(user, callback)
    }

    fun uploadImage(folderName: String, imageUri: Uri, randomImageKey: String, callback: () -> Unit) {
        firebaseModel.uploadPicture(folderName, imageUri, randomImageKey, callback)
    }

    fun getImage(folderName: String, randomImageKey: String, callback: (Uri?) -> Unit) {
        firebaseModel.getPicture(folderName, randomImageKey, callback)
    }
}