package com.lakue.itunesgreendaysearch.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelLazy
import com.lakue.itunesgreendaysearch.OnThrottleClickListener
import com.lakue.itunesgreendaysearch.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
        @LayoutRes val layoutId: Int
) : AppCompatActivity() {

    var mToast: Toast? = null
    var isShowToast = false

    lateinit var binding: B

    private val viewModelClass = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>).kotlin

    protected open val viewModel by ViewModelLazy(
            viewModelClass,
            { viewModelStore },
            { defaultViewModelProviderFactory }
    )

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        init()
    }

    abstract fun init()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
    }

    protected fun View.onThrottleClick(action: (v: View) -> Unit) {
        val listener = View.OnClickListener { action(it) }
        setOnClickListener(OnThrottleClickListener(listener))
    }
}