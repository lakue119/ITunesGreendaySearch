package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseAdapter
import com.lakue.itunesgreendaysearch.base.BaseViewHolder
import com.lakue.itunesgreendaysearch.databinding.ItemMusicBinding

class HomeAdapter(val viewModel: HomeViewModel) : BaseAdapter() {
    var dataCount = 0

    fun setCount(count: Int) {
        dataCount = count
        notifyDataSetChanged()
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

    override fun getItemCount() = dataCount

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when(holder){
            is HomeViewHolder -> {
                holder.onBind(viewModel)
            }
        }
    }

    /**
     * ViewHolder
     */
    inner class HomeViewHolder(private val binding: ItemMusicBinding) :
        BaseViewHolder(binding.root) {
        fun onBind(vm: HomeViewModel) {
            binding.apply {
                this.vm = vm
                this.pos = adapterPosition
            }
        }
    }
}