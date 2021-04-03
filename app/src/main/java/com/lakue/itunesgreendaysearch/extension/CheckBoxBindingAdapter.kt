package com.lakue.itunesgreendaysearch.extension

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.lakue.itunesgreendaysearch.model.Track

@BindingAdapter("onCheckChangeAction", "track")
fun CheckBox.onCheckChangeAction(f: Function2<Track, Boolean, Unit>?, track: Track) {
    if(track == null){
        return
    }
    if (f == null) setOnClickListener(null)
    else setOnClickListener {
        f(track,isChecked)
    }
}

@BindingAdapter("favoriteChecked")
fun CheckBox.onFavoriteChecked(checked: Boolean) {
    this.isChecked = checked
}