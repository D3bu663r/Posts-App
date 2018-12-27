package com.rafael.posts.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun all(): List<Post>

    @Insert
    fun insertAll(vararg posts: Post)
}