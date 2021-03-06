package com.lakue.itunesgreendaysearch.extension

import android.view.View
import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.utils.LogUtil

/**
 * RecyclerView Adapter
 */
@BindingAdapter("setAdapter")
fun RecyclerView.setAdaper(
    adapter: RecyclerView.Adapter<*>
){
    this.apply {
        setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("onBottomCatchEvent")
fun RecyclerView.onBottomCatchEvent(f: Function1<Int, Unit>?) {

    if (f == null) //addOnScrollListener(null)
    else addOnScrollListener(object :RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            f(lastVisibleItemPosition)
        }
    })
}