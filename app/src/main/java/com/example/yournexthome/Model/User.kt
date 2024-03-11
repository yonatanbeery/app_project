package com.example.yournexthome.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val email: String,
    var username: String,
    val profilePicture: String) {

    companion object{
        fun fromJSON(json:Map<String, Any>, id: String):User {
            val email = json["email"].toString()
            val username = json["username"].toString()
            val profilePicture = json["profilePicture"].toString()
            return User(id, email, username, profilePicture);
        }
    }

    val json:Map<String, Any> get() {
        return hashMapOf(
            "email" to email,
            "username" to username,
            "profilePicture" to profilePicture
        )
    }

}