package com.royal.musicapplication.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.royal.musicapplication.R
import com.royal.musicapplication.databinding.SeparatorViewItemBinding
import com.royal.musicapplication.feature.Track

class SeparatorViewHolder(val binding: SeparatorViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        binding.artistName.text = track.artistName
        binding.artistId.text = track.artistId.toString()
    }

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val binding: SeparatorViewItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.separator_view_item,
                parent,
                false
            )
            return SeparatorViewHolder(binding)
        }
    }
}
