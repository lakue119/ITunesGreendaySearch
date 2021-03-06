package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseAdapter
import com.lakue.itunesgreendaysearch.base.BaseViewHolder
import com.lakue.itunesgreendaysearch.databinding.ItemMusicBinding
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.utils.LogUtil

class HomeAdapter(val viewModel: HomeViewModel) : BaseAdapter() {

    val TAG = "HomeAdapter"
    var musiclist = ArrayList<Track>()

    fun addItems(items: ArrayList<Track>) {
        var pos = musiclist.size
        musiclist.addAll(items)
        notifyItemRangeInserted(pos, musiclist.size - 1)
    }

    fun favoriteTrackChanged(pos: Int, isFavorite: Boolean){
        musiclist[pos].favorite = isFavorite
        LogUtil.i(TAG, "$pos :  ${musiclist[pos]}    isFavorite : $isFavorite")
        notifyItemChanged(pos)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        DataBindingUtil.inflate<ItemMusicBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_music,
                parent,
                false
        ).let {
            return HomeViewHolder(it)
        }
    }

    override fun getItemCount() = musiclist.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> {
                holder.onBind(musiclist[position])
            }
        }
    }

    /**
     * ViewHolder
     */
    inner class HomeViewHolder(private val binding: ItemMusicBinding) :
            BaseViewHolder(binding.root) {
        fun onBind(music: Track) {
            binding.apply {
                LogUtil.i(TAG, "Bind / $adapterPosition :  ${music}")
                this.vm = viewModel
                this.music = music
                this.pos = adapterPosition
            }
        }
    }
}