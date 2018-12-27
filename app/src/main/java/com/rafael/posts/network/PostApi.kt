package com.rafael.posts.network

import com.rafael.posts.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun posts(): Observable<List<Post>>
}