package com.rafael.posts.utils.dataBinder

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.rafael.posts.utils.extension.getParentActivity


@BindingAdapter("loadingVisibility")
fun loadingRefresh(view: SwipeRefreshLayout, loadingVisibility: MutableLiveData<Boolean>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && loadingVisibility != null) {
        loadingVisibility.observe(parentActivity, Observer { value -> view.isRefreshing = value ?: false })
    }
}

@BindingAdapter("text")
fun text(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("adapter")
fun adapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}