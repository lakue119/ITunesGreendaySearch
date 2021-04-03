package com.lakue.itunesgreendaysearch.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelLazy
import com.lakue.itunesgreendaysearch.IGSApplication
import com.lakue.itunesgreendaysearch.OnThrottleClickListener
import com.lakue.itunesgreendaysearch.utils.Event
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
        @LayoutRes val layoutId: Int
) : AppCompatActivity() {

    var mToast: Toast? = null
    var isShowToast = false

    lateinit var binding: B

    @SuppressWarnings("unchecked")
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

    protected infix fun <T> LiveData<Event<T>>.eventObserve(action: (T) -> Unit) {
        observe(this@BaseActivity) {
            it.get(action)
        }
    }

    protected fun showLoading(){
        IGSApplication.getInstance().showLoading(this)
    }

    protected fun hideLoading(){
        IGSApplication.getInstance().hideLoading()
    }
}