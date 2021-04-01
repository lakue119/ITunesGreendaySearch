package com.lakue.itunesgreendaysearch.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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