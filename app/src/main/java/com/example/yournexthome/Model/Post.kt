package com.example.yournexthome.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: String,
    val header: String,
    val description: String,
    val postImage:String,
    var isChecked: Boolean)
