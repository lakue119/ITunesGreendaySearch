package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import android.os.Bundle
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseFragment
import com.lakue.itunesgreendaysearch.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Fresco.initialize(mContext)

        binding.apply{
            vm = viewModel
        }

        viewModel.apply{
            fetchFavoriteTrack()
            fetchiTunesMusic()
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}