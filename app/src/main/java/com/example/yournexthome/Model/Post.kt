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
    val freeText: String) {

    val json:Map<String, Any> get() {
        return hashMapOf(
            "city" to city,
            "price" to price,
            "areaSize" to areaSize,
            "bedrooms" to bedrooms,
            "bathrooms" to bathrooms,
            "name" to name,
            "phone" to phone,
            "freeText" to freeText
        )
    }

}