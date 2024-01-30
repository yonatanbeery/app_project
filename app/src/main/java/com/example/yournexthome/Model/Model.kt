package com.example.yournexthome.Model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.yournexthome.dao.AppLocalDB
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class Model private constructor(){
    private var database = AppLocalDB.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: Model = Model()
    }

    interface getAllPostsListener {
        fun onComplete(posts:List<Post>)
    }

    fun getAllPosts(callback: (List<Post>)-> Unit) {
        executor.execute{
            Thread.sleep(5000)
            val posts = database.PostDao().gatAll()
            mainHandler.post {
                callback(posts)
            }
        }
    }

    fun addPost(post: Post, callback: ()-> Unit) {
        executor.execute{
            database.PostDao().insert(post)
            mainHandler.post {
                callback()
            }
        }
    }
}