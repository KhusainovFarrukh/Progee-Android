package kh.farrukh.progee.ui.language_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.databinding.ItemFrameworkBinding
import kh.farrukh.progee.utils.SingleBlock
import kh.farrukh.progee.utils.loadImageById

/**
 *Created by farrukh_kh on 6/24/22 3:36 AM
 *kh.farrukh.progee.ui.language_details
 **/
class FrameworkAdapter(
    private val onFrameworkClick: SingleBlock<Long>,
) : PagingDataAdapter<Framework, FrameworkAdapter.FrameworkVH>(FRAMEWORK_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FrameworkVH(
        ItemFrameworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FrameworkVH, position: Int) {
        getItem(position)?.let { movie -> holder.bindFramework(movie) }
    }

    //function for setting custom LoadStateAdapters, because default one does not show loadState on initial request
    fun withCustomLoadStateHeaderAndFooter(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>,
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
            footer.loadState = when (loadStates.refresh) {
                is LoadState.NotLoading -> loadStates.append
                else -> loadStates.refresh
            }
        }
        return ConcatAdapter(header, this, footer)
    }

    inner class FrameworkVH(private val binding: ItemFrameworkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onFrameworkClick(
                    getItem(bindingAdapterPosition)?.id
                        ?: throw NullPointerException("There is no framework")
                )
            }
        }

        fun bindFramework(framework: Framework) = with(binding) {

            tvTitle.text = framework.name
            imvImage.loadImageById(framework.image.id)
        }
    }

    companion object {
        val FRAMEWORK_ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Framework>() {
            override fun areItemsTheSame(oldItem: Framework, newItem: Framework) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Framework, newItem: Framework) =
                oldItem.name == newItem.name &&
                        oldItem.image == newItem.image
        }
    }
}