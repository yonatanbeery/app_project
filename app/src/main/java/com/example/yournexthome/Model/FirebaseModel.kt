package com.example.yournexthome.Model

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.UUID

class FirebaseModel {
    var POSTS_COLLECTION_NAME = "posts"
    var USERS_COLLECTION_NAME = "users"
    val db = Firebase.firestore
    val firebaseStoreReference = FirebaseStorage.getInstance().reference

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

    fun updatePost(post: Post, callback: ()-> Unit) {
        db.collection(POSTS_COLLECTION_NAME)
            .document(post.id)
            .update(mapOf(
                "city" to post.city,
                "price" to post.price,
                "areaSize" to post.areaSize,
                "bedrooms" to post.bedrooms,
                "bathrooms" to post.bathrooms,
                "name" to post.name,
                "phone" to post.phone,
                "freeText" to post.freeText
            ))
            .addOnSuccessListener { documentReference ->
                callback()
            }
    }

    fun getPost(postId: String, callback: (Post?) -> Unit) {
        db.collection(POSTS_COLLECTION_NAME)
            .document(postId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentSnapshot = task.result
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val post = documentSnapshot.data?.let { Post.fromJSON(it, documentSnapshot.id) }
                        if (post != null) {
                            callback(post)
                        }
                    } else {
                        callback(null)

                    }
                } else {
                    callback(null)
                }

            }
    }

    fun getUser(email: String, callback: (User?) -> Unit) {
        db.collection(USERS_COLLECTION_NAME)
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentSnapshot = task.result.documents[0]
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val user = documentSnapshot.data?.let { User.fromJSON(it, documentSnapshot.id) }
                        if (user != null) {
                            callback(user)
                        }
                    } else {
                        callback(null)

                    }
                } else {
                    callback(null)
                }

            }
    }

    fun addUser(user: User, callback: ()-> Unit) {
        db.collection(USERS_COLLECTION_NAME)
            .add(user.json)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                callback()
            }
    }

    fun updateUser(user: User, callback: ()-> Unit) {
        Log.i("USER", "updating user" + user.toString())
        db.collection(USERS_COLLECTION_NAME)
            .document(user.id)
            .update(mapOf(
                "username" to user.username,
                "profilePicture" to user.profilePicture
            ))
            .addOnSuccessListener { documentReference ->
                callback()
            }
    }

    fun getFilteredPosts(creatorId: String?, city: String?, minPrice: Int?, maxPrice: Int?, minBeds: Int?, minBaths: Int?, callback: (List<Post>) -> Unit) {
        try {
            var query: Query = db.collection(POSTS_COLLECTION_NAME)
            if (city != null) {
                query = query.whereEqualTo("city", city)
            }
            if (minPrice != null) {
                query = query.whereGreaterThanOrEqualTo("price", minPrice)
            }
            if (maxPrice != null) {
                query = query.whereLessThanOrEqualTo("price", maxPrice)
            }

            if (creatorId != "") {
                query = query.whereEqualTo("creatorId", creatorId)
            }

            query.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val posts: MutableList<Post> = mutableListOf()
                        for (document in task.result!!) {
                            val bedrooms = document.data["bedrooms"] as? Long
                            val bathrooms = document.data["bathrooms"] as? Long

                            if (bedrooms != null && bathrooms != null &&
                                (minBeds == null || bedrooms >= minBeds) &&
                                (minBaths == null || bathrooms >= minBaths)
                            ) {
                                posts.add(Post.fromJSON(document.data, document.id))
                            }
                        }
                        callback(posts)
                    } else {
                        callback(emptyList())
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun uploadPicture(folderName: String, imageUri: Uri, randomImageKey: String, callback: () -> Unit) {
        val imagesRef = firebaseStoreReference.child("$folderName/$randomImageKey")
        try {
            imagesRef.putFile(imageUri).addOnCompleteListener {
                callback()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPicture(folderName: String, randomImageKey: String, callback: (Uri?) -> Unit) {
        val imagesRef = firebaseStoreReference.child("$folderName/$randomImageKey")
        try {
            imagesRef.downloadUrl.addOnCompleteListener {task ->
                callback(task.result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
