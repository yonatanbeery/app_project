package com.example.yournexthome.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yournexthome.Model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM Post " +
            "WHERE (:creatorId IS NULL OR creatorId = :creatorId)" +
            "AND (:city IS NULL OR city = :city)" +
            "AND (:minPrice IS NULL OR price > :minPrice)" +
            "AND (:maxPrice IS NULL OR price < :maxPrice)" +
            "AND (:minBeds IS NULL OR bedrooms > :minBeds)" +
            "AND (:minBaths IS NULL OR bathrooms > :minBaths)")
    fun gatFilteredPosts(creatorId: String?, city: String?, minPrice: Int?, maxPrice: Int?, minBeds: Int?, minBaths: Int?) : LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg post: Post)

    @Delete()
    fun delete(post: Post)

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getPostById(id: String):LiveData<Post>
}