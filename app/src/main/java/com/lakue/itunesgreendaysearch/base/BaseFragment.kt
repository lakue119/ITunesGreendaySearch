package com.lakue.itunesgreendaysearch.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import com.lakue.itunesgreendaysearch.IGSApplication
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
        @LayoutRes val layoutId: Int
) : Fragment() {
    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    lateinit var mContext: Context

    private val viewModelClass = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>).kotlin

    protected val viewModel by ViewModelLazy(
            viewModelClass,
            { viewModelStore },
            { defaultViewModelProviderFactory }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun viewModel(action: VM.() -> Unit) {
        viewModel.run(action)
    }

    protected fun showToast(msg: String) =
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    protected fun showLoading(){
        IGSApplication.getInstance().showLoading(mContext as Activity)
    }

    protected fun hideLoading(){
        IGSApplication.getInstance().hideLoading()
    }
}