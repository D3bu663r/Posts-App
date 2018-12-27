package com.rafael.posts.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rafael.posts.model.Post
import com.rafael.posts.model.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}