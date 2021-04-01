package com.lakue.itunesgreendaysearch.utils

import android.util.Log
import com.lakue.itunesgreendaysearch.BuildConfig

object LogUtil {

    val MAX_INDEX = 500

    fun w(TAG: String, message: String) {

        if (BuildConfig.DEBUG) {
            Log.w(TAG, message)
        }
    }

    fun i(TAG: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, message)

        }
    }

    fun d(TAG: String, message: String) {
        if (BuildConfig.DEBUG) {
            LogLineBreak(TAG, message)
        }
    }

    fun v(TAG: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, message)
        }
    }

    fun e(TAG: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }

    fun e(TAG: String?, message: String, tr: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e(
                    TAG, """
     $message
     ${Log.getStackTraceString(tr)}
     """.trimIndent()
            )
        }
    }

    fun LogLineBreak(TAG:String, str: String) {
        if (str.length > MAX_INDEX) {    // 텍스트가 3000자 이상이 넘어가면 줄
            Log.i(TAG, str.substring(0, MAX_INDEX))
            LogLineBreak(TAG, str.substring(MAX_INDEX))
        } else {
            Log.i(TAG, str)
        }
    }

}