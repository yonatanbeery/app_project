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

    fun getPost(postId: String, callback: (Post?)-> Unit) {
        firebaseModel.getPost(postId, callback)
//        executor.execute{
//            database.PostDao().insert(post)
//            mainHandler.post {
//                callback()
//            }
//        }
    }
}