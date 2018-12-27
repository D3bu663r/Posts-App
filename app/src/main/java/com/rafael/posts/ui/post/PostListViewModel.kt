package com.rafael.posts.ui.post

import android.arch.lifecycle.MutableLiveData
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.rafael.posts.R
import com.rafael.posts.base.BaseViewModel
import com.rafael.posts.model.Post
import com.rafael.posts.model.PostDao
import com.rafael.posts.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val refreshPost = SwipeRefreshLayout.OnRefreshListener { loadPost() }

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPost() }

    val postListAdapter: PostListAdapter = PostListAdapter()

    init {
        loadPost()
    }

    private fun loadPost() {
        subscription = Observable.fromCallable { postDao.all() }
            .concatMap { dbPosts ->
                if (dbPosts.isEmpty())
                    postApi.posts().concatMap { apiPosts ->
                        postDao.insertAll(*apiPosts.toTypedArray())
                        Observable.just(apiPosts)
                    }
                else
                    Observable.just(dbPosts)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { posts -> onRetrievePostListSuccess(posts) },
                { onRetrievePostListError() }
            )
    }


    private fun onRetrievePostListStart() {
        loadingVisibility.value = true
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = false
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}