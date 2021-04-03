package com.lakue.itunesgreendaysearch.ui.bottomnavagation.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseFragment
import com.lakue.itunesgreendaysearch.databinding.FragmentFavoriteBinding
import com.lakue.itunesgreendaysearch.databinding.FragmentHomeBinding
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.ui.bottomnavagation.home.HomeFragment
import com.lakue.itunesgreendaysearch.ui.bottomnavagation.home.HomeViewModel
import com.lakue.itunesgreendaysearch.ui.music.MusicActivity
import com.lakue.itunesgreendaysearch.ui.music.MusicActivity.Companion.EXTRA_POSITION
import com.lakue.itunesgreendaysearch.ui.music.MusicActivity.Companion.EXTRA_TRACK
import com.lakue.itunesgreendaysearch.utils.ActivityContract
import com.lakue.itunesgreendaysearch.utils.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, HomeViewModel>(R.layout.fragment_favorite) {

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityContract()
    ) {
        it?.let {
            var changeIds = it.getSerializableExtra("result") as ArrayList<Int>
            viewModel.apply {
                fetchFavoriteTrack()
                favoriteTrackCheck(changeIds)
            }
            LogUtil.d("KQWJRKLQWJRLKQQ", changeIds.toString())
        }.run {
//            finish()
        }
    }

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

            musickDetailEvent eventObserve { musicDetail(it.first, it.second, it.third) }

        }

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    fun musicDetail(items: ArrayList<Track>, pos: Int, homePos: Int) {
        val intent = Intent(mContext, MusicActivity::class.java)
        intent.putExtra(EXTRA_TRACK, items)
        intent.putExtra(EXTRA_POSITION, pos)
        intent.putExtra(MusicActivity.EXTRA_ORIGIN_POSITION, homePos)
        launcher.launch(intent)
    }
}