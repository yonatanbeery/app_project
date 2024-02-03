package com.example.yournexthome.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: String,
    val city: String,
    val price: Int,
    val areaSize: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val name: String,
    val phone: String,
    val freeText: String)
