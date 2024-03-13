package com.example.yournexthome.Model

import android.os.Looper
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

    interface getAllPostsListener {
        fun onComplete(posts:List<Post>)
    }

    fun getAllPosts(callback: (List<Post>)-> Unit) {
        firebaseModel.getAllPosts(callback)
//        executor.execute{
//            Thread.sleep(2000)
//            val posts = database.PostDao().gatAll()
//            mainHandler.post {
//                callback(posts)
//            }
//        }
    }

    fun addPost(post: Post, callback: ()-> Unit) {
        firebaseModel.addPost(post, callback)
//        executor.execute{
//            database.PostDao().insert(post)
//            mainHandler.post {
//                callback()
//            }
//        }
    }

    fun updatePost(post: Post, callback: ()-> Unit) {
        firebaseModel.addPost(post, callback)
    }

    fun getPost(postId: String, callback: (Post?)-> Unit) {
        firebaseModel.getPost(postId, callback)
//        executor.execute{
//            database.PostDao().insert(post)
//            mainHandler.post {
//                callback()
//            }
//        }
    }

    fun getFilteredPosts(creatorId: String?, city: String?, minPrice: Int?, maxPrice: Int?, minBeds: Int?, minBaths: Int?, callback: (List<Post>)-> Unit) {
            firebaseModel.getFilteredPosts(creatorId, city, minPrice, maxPrice, minBeds, minBaths, callback)
//        executor.execute{
//            database.PostDao().insert(post)
//            mainHandler.post {
//                callback()
//            }
//        }
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
}