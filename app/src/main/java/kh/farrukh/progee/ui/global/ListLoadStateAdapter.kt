package kh.farrukh.progee.ui.global

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.databinding.ItemListLoadStateBinding
import kh.farrukh.progee.utils.EmptyBlock

/**
 *Created by farrukh_kh on 6/23/22 10:43 AM
 *kh.farrukh.progee.ui.global
 **/
class ListLoadStateAdapter(private val retry: EmptyBlock) :
    LoadStateAdapter<ListLoadStateAdapter.ListLoadStateVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ListLoadStateVH(
        ItemListLoadStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ListLoadStateVH, loadState: LoadState) {
        holder.bindLoadState(loadState)
    }

    inner class ListLoadStateVH(private val binding: ItemListLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bindLoadState(loadState: LoadState) = with(binding) {
            lottieLoading.isVisible = loadState is LoadState.Loading
            txvError.isVisible = loadState !is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
        }
    }
}