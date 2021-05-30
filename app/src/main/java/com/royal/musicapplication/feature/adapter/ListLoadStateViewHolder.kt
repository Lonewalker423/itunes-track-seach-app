package com.royal.musicapplication.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.royal.musicapplication.R
import com.royal.musicapplication.databinding.MusicLoadStateFooterViewItemBinding

class ListLoadStateViewHolder(
    private val binding: MusicLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ListLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.music_load_state_footer_view_item, parent, false)
            val binding = MusicLoadStateFooterViewItemBinding.bind(view)
            return ListLoadStateViewHolder(binding, retry)
        }
    }
}
