package com.lakue.itunesgreendaysearch.extension

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

inline fun <reified T> AppCompatActivity.startActivity(
    vararg pairs: Pair<String, Any?>,
) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*pairs))
    })
}