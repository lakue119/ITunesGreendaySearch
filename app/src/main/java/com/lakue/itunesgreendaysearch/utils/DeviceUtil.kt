package com.lakue.itunesgreendaysearch.utils

import com.lakue.itunesgreendaysearch.utils.BaseUtils.context

object DeviceUtil {
    val deviceWidth: Int
        get() = context.resources.displayMetrics.widthPixels

    val deviceHeight: Int
        get() = context.resources.displayMetrics.heightPixels
}