package com.lakue.itunesgreendaysearch

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import com.facebook.drawee.backends.pipeline.Fresco
import com.lakue.itunesgreendaysearch.utils.BaseUtils.init
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IGSApplication : Application() {

    var progressDialog: AppCompatDialog? = null

    companion object{
        lateinit var IGSApplication: IGSApplication

        fun getInstance(): IGSApplication{
            return IGSApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        IGSApplication = this
        init(this)
        Fresco.initialize(this)
    }

    fun showLoading(activity: Activity){
        if(activity == null || activity.isFinishing){
            return
        }

        if(progressDialog != null && progressDialog?.isShowing!!){

        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog?.apply{
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.progress_loading)
                show()
            }
        }
    }

    fun hideLoading(){
        if(progressDialog != null && progressDialog?.isShowing!!){
            progressDialog?.dismiss()
        }
    }
}