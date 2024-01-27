package com.example.yournexthome.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yournexthome.Model.Post
import com.example.yournexthome.base.MyApplication

@Database(entities = [Post::class], version = 1)
abstract class AppLocalDBRepository : RoomDatabase() {
    abstract fun PostDao():PostDao
}
object AppLocalDB {
    val db: AppLocalDBRepository by lazy {
        val context = MyApplication.Globals.appContext ?: throw IllegalStateException("Application context not available")
        Room.databaseBuilder(context, AppLocalDBRepository::class.java,"dbFileName.db")
            .fallbackToDestructiveMigration().build()
    }
}