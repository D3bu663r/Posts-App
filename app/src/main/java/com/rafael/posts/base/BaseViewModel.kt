package com.rafael.posts.base

import android.arch.lifecycle.ViewModel
import com.rafael.posts.injection.component.DaggerViewModelInjector
import com.rafael.posts.injection.module.NetworkModule
import com.rafael.posts.ui.post.PostListViewModel

abstract class BaseViewModel : ViewModel() {

    private val injector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}
