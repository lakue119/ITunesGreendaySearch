package com.lakue.itunesgreendaysearch.extension

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("imgUrl")
fun SimpleDraweeView.setImageUrl(
    url: String
){
    this.setImageURI(Uri.parse(url))
}
