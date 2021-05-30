package com.royal.musicapplication.feature

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.royal.musicapplication.R
import com.royal.musicapplication.core.base.BaseFragment
import com.royal.musicapplication.core.extension.invisible
import com.royal.musicapplication.core.extension.visible
import com.royal.musicapplication.databinding.FragmentMusicListBinding
import com.royal.musicapplication.feature.adapter.ListLoadStateAdapter
import com.royal.musicapplication.feature.adapter.MusicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicListFragment : BaseFragment<FragmentMusicListBinding>() {
    private val adapter = MusicListAdapter()
    private var searchJob: Job? = null
    private val viewModel: MusicListViewModel by viewModels()
    override fun layoutId() = R.layout.fragment_music_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        initAdapter()
        val query =  DEFAULT_QUERY
        search(query)
        initSearch(query)
        binding.retryButton.setOnClickListener { adapter.retry() }
    }


    private fun initAdapter() {
        //setting up load state adapter
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ListLoadStateAdapter { adapter.retry() },
            footer = ListLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error,
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    //search according to input string
    private fun initSearch(query: String) {
        binding.searchMusic.setText(query)
        binding.searchMusic.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchMusic.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }

        }
    }


    private fun updateRepoListFromInput() {
        binding.searchMusic.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visible()
            binding.list.invisible()
        } else {
            binding.emptyList.invisible()
            binding.list.visible()
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "travis"
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchMusic(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}