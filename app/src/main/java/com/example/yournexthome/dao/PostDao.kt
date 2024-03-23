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
    @Query("SELECT * FROM Post WHERE (:creatorId IS NULL OR creatorId = :creatorId)")
    fun gatAllPosts(creatorId: String?) : LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg post: Post)

    @Delete()
    fun delete(post: Post)

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getPostById(id: String):LiveData<Post>
}