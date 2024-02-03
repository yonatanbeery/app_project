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
                        val city = json.data.get("city").toString()
                        val price = json.data.get("price").toString()
                        val areaSize = json.data.get("areaSize").toString()
                        val bedrooms = json.data.get("bedrooms").toString()
                        val bathrooms = json.data.get("bathrooms").toString()
                        val name = json.data.get("name").toString()
                        val phone = json.data.get("phone").toString()
                        val freeText = json.data.get("freeText").toString()

                        val post = Post(json.id, city, price.toInt(), areaSize.toInt(), bedrooms.toInt(), bathrooms.toInt(), name, phone, freeText)
                        posts.add(post)
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
