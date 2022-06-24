package kh.farrukh.progee.ui.global

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.databinding.ItemHorizontalListLoadStateBinding
import kh.farrukh.progee.databinding.ItemVerticalListLoadStateBinding
import kh.farrukh.progee.utils.EmptyBlock

/**
 *Created by farrukh_kh on 6/23/22 10:43 AM
 *kh.farrukh.progee.ui.global
 **/
class ListLoadStateAdapter(private val isVertical: Boolean = true, private val retry: EmptyBlock) :
    LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder = if (isVertical) {
        VerticalListLoadStateVH(
            ItemVerticalListLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    } else {
        HorizontalListLoadStateVH(
            ItemHorizontalListLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        if (isVertical) {
            (holder as ListLoadStateAdapter.VerticalListLoadStateVH).bindLoadState(loadState)
        } else {
            (holder as ListLoadStateAdapter.HorizontalListLoadStateVH).bindLoadState(loadState)
        }
    }

    inner class VerticalListLoadStateVH(private val binding: ItemVerticalListLoadStateBinding) :
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

    inner class HorizontalListLoadStateVH(private val binding: ItemHorizontalListLoadStateBinding) :
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