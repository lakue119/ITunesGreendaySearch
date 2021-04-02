package com.lakue.itunesgreendaysearch.ui.bottomnavagation.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseFragment
import com.lakue.itunesgreendaysearch.databinding.FragmentFavoriteBinding
import com.lakue.itunesgreendaysearch.databinding.FragmentHomeBinding
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.ui.bottomnavagation.home.HomeFragment
import com.lakue.itunesgreendaysearch.ui.bottomnavagation.home.HomeViewModel
import com.lakue.itunesgreendaysearch.ui.music.MusicActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, HomeViewModel>(R.layout.fragment_favorite) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            vm = viewModel
        }

        viewModel.apply{
            fetchFavoriteAll()
            loading.observe(this@FavoriteFragment, Observer {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            })

            musickDetailEvent eventObserve { musicDetail(it) }
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    fun musicDetail(item: Track) {
        val intent = Intent(mContext, MusicActivity::class.java)
        intent.putExtra(MusicActivity.EXTRA_TRACK,item)
        startActivity(intent)
    }
}