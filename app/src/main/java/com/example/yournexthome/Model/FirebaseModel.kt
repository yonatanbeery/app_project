package com.example.yournexthome.Model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirebaseModel {
    val db = Firebase.firestore

    fun getAllPosts(callback: (List<Post>)-> Unit) {

    }

    fun addPost(post: Post, callback: ()-> Unit) {
        val post = hashMapOf(
            "city" to post.city,
            "price" to post.price,
            "areaSize" to post.areaSize,
            "bedrooms" to post.bedrooms,
            "bathrooms" to post.bathrooms,
            "name" to post.name,
            "phone" to post.phone,
            "freeText" to post.freeText
        )
        db.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                callback()
            }
    }


}

/*

// Create a new user with a first and last name


// Add a new document with a generated ID
db.collection("users")
.add(user)
.addOnSuccessListener { documentReference ->
    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
}
.addOnFailureListener { e ->
    Log.w("TAG", "Error adding document", e)
}
 */