package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import android.os.Bundle
import android.view.View
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseFragment
import com.lakue.itunesgreendaysearch.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            tvTitle.text = "야호"
        }

        viewModel.apply{
            fetchiTunesMusic()
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}