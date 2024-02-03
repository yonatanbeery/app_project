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

    companion object{
        fun fromJSON(json:Map<String, Any>, id: String):Post {
            val city = json["city"].toString()
            val price = json["price"].toString()
            val areaSize = json["areaSize"].toString()
            val bedrooms = json["bedrooms"].toString()
            val bathrooms = json["bathrooms"].toString()
            val name = json["name"].toString()
            val phone = json["phone"].toString()
            val freeText = json["freeText"].toString()
            return Post(id, city, price.toInt(), areaSize.toInt(), bedrooms.toInt(), bathrooms.toInt(), name, phone, freeText)
        }
    }

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