package kh.farrukh.progee.ui.language_details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.R
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.databinding.ItemReviewBinding
import kh.farrukh.progee.utils.SingleBlock
import kh.farrukh.progee.utils.calculateCreatedOn
import kh.farrukh.progee.utils.loadImageById

/**
 *Created by farrukh_kh on 6/25/22 1:51 AM
 *kh.farrukh.progee.ui.language_details
 **/
class ReviewAdapter(
    private val onReviewClick: SingleBlock<Long>,
    private val onLikeClick: SingleBlock<Long>,
    private val onDislikeClick: SingleBlock<Long>
) : PagingDataAdapter<Review, ReviewAdapter.ReviewVH>(REVIEW_ITEM_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewVH(
        ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
        getItem(position)?.let { movie -> holder.bindReview(movie) }
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

    inner class ReviewVH(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onReviewClick(
                    getItem(bindingAdapterPosition)?.id
                        ?: throw NullPointerException("There is no review")
                )
            }
            binding.txvLike.setOnClickListener {
                onLikeClick(
                    getItem(bindingAdapterPosition)?.id
                        ?: throw NullPointerException("There is no review")
                )
            }
            binding.txvDislike.setOnClickListener {
                onDislikeClick(
                    getItem(bindingAdapterPosition)?.id
                        ?: throw NullPointerException("There is no review")
                )
            }
            binding.txvReadMore.setOnClickListener {
                binding.txvReadMore.text =
                    if (binding.txvBody.isExpanded) "Read more" else "Read less"
                binding.txvReadMore.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    if (binding.txvBody.isExpanded) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up,
                    0
                )
                binding.txvBody.toggle()
            }
        }

        fun bindReview(review: Review) = with(binding) {
            imvAuthorImage.loadImageById(
                review.author.image.id,
                placeholder = R.drawable.ic_account_circle
            )
            txvAuthorName.text = review.author.name
            txvAuthorUsername.text = "@${review.author.username}"

            txvReviewValue.text = review.value.name
            txvScore.text = "Score: ${review.score}"
            txvCreatedAt.text = review.createdAt.calculateCreatedOn(root.context)
            txvBody.text = review.body
            txvBody.setInterpolator(OvershootInterpolator())
            txvLike.text = review.upVotes.size.toString()
            txvDislike.text = review.downVotes.size.toString()
        }
    }

    companion object {
        val REVIEW_ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem
        }
    }
}