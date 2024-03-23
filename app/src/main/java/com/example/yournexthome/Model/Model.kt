package com.example.yournexthome.Model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yournexthome.dao.AppLocalDB
import java.util.concurrent.Executors

class Model private constructor(){
    enum class LoadingState{
        LOADING,
        LOADED
    }

    private var database = AppLocalDB.db
    private var executor = Executors.newSingleThreadExecutor()
    private var firebaseModel = FirebaseModel()
    val postsLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

    companion object {
        val instance: Model = Model()
    }

    fun addPost(post: Post, callback: ()-> Unit) {
        firebaseModel.addPost(post) {
            refreshPosts()
            callback()
        }
    }

    fun updatePost(post: Post, callback: ()-> Unit) {
        firebaseModel.updatePost(post, callback)
    }

    fun getPost(postId: String, callback: (Post?)-> Unit) {
        firebaseModel.getPost(postId, callback)
    }

    fun getAllPosts(creatorId: String?):LiveData<List<Post>>? {
        return database.PostDao().gatAllPosts(creatorId)
    }


    fun refreshPosts() {
        postsLoadingState.value = LoadingState.LOADING

        var lastUpdated: Long = Post.lastUpdated

        firebaseModel.getAllPosts(lastUpdated) { list ->
            executor.execute {
                var time = lastUpdated
                for(post in list) {
                    database.PostDao().insert(post)
                    post.lastUpdated?.let {
                        if(time < it) time = post.lastUpdated ?: (System.currentTimeMillis() / 1000)
                    }
                }
                Post.lastUpdated = time
                postsLoadingState.postValue(LoadingState.LOADED)
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