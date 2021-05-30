package com.royal.musicapplication.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.royal.musicapplication.R
import com.royal.musicapplication.core.extension.formateMilliSeccond
import com.royal.musicapplication.core.extension.loadFromUrl
import com.royal.musicapplication.databinding.MusicListItemBinding
import com.royal.musicapplication.feature.Track
import java.lang.String
import java.util.concurrent.TimeUnit
import kotlin.math.min

class MusicViewHolder(val binding: MusicListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(track: Track?) {
        binding.trackName.text = track?.trackName
        binding.artistName.text = track?.artistName
        track?.trackTimeMillis?.let {
            binding.trackTime.text = formateMilliSeccond(it.toLong())
        }
        track?.artworkUrl100?.let {
            binding.coverArt.loadFromUrl(it)
        }
    }


    companion object {
        fun create(parent: ViewGroup): MusicViewHolder {
            val binding: MusicListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.music_list_item,
                parent,
                false
            )
            return MusicViewHolder(binding)
        }
    }
}
