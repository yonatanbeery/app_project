package com.example.yournexthome.Model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings

class FirebaseModel {
    var POSTS_COLLECTION_NAME = "posts"
    val db = Firebase.firestore

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
            //setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }

    fun getAllPosts(callback: (List<Post>)-> Unit) {
        db.collection(POSTS_COLLECTION_NAME).get().addOnCompleteListener() {
            when (it.isSuccessful) {
                true -> {
                    val posts: MutableList<Post> = mutableListOf()
                    for (json in it.result) {
                        posts.add(Post.fromJSON(json.data, json.id))
                    }
                    callback(posts)
                } false -> callback(listOf())
            }
        }
    }

    fun addPost(post: Post, callback: ()-> Unit) {
        db.collection(POSTS_COLLECTION_NAME)
            .add(post.json)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                callback()
            }
    }
}