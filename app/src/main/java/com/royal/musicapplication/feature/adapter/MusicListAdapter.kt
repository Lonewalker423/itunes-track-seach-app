package com.royal.musicapplication.feature.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.royal.musicapplication.R
import com.royal.musicapplication.feature.UiModel

class MusicListAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.separator_view_item) {
            SeparatorViewHolder.create(parent)
        } else {
            MusicViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.MusicItem -> R.layout.music_list_item
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.MusicItem -> (holder as MusicViewHolder).bind(uiModel.track)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.track)
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.MusicItem && newItem is UiModel.MusicItem &&
                        oldItem.track.trackId == newItem.track.trackId) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.track == newItem.track)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }
}