package kh.farrukh.progee.ui.languages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.databinding.ItemLanguageBinding
import kh.farrukh.progee.utils.SingleBlock

/**
 *Created by farrukh_kh on 6/23/22 10:39 AM
 *kh.farrukh.progee.ui.languages
 **/
class LanguageAdapter(
    private val onLanguageClick: SingleBlock<Long>,
) : PagingDataAdapter<Language, LanguageAdapter.LanguageVH>(MOVIE_ITEM_DIFF_CALLBACK) {

    //variables to know if there is header/footer in the list or not
    private var hasHeader = false
    private var hasFooter = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LanguageVH(
        ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LanguageVH, position: Int) {
        getItem(position)?.let { movie -> holder.onBindMovie(movie) }
    }

    override fun getItemViewType(position: Int): Int {

        //if there is header in the list...
        return if (hasHeader) {
            //...and also there is footer, position of the footer will be 'itemCount + 1'

            /*
            Proof to it:

            if we change 'itemCount + 1' to 'itemCount' only and do that:

            scroll list to very bottom and when footer is appeared
            fast scroll to top of the list to have header, and fast scroll bottom again to see footer,
            footer will be with spanSize = 1 (half of screen width) and
            last movie item with spanSize = 2 (full screen width)

            It can be seen clearly when there is low network speed and maxSize is set to 60
            */
            if (hasFooter && position == itemCount + 1) {
                VIEW_TYPE_LOADING

                //...and if position is 0, it will be header
            } else if (position == 0) {
                VIEW_TYPE_LOADING

                //...else it is movie item
            } else {
                VIEW_TYPE_LANGUAGE
            }

            //if there isn't header in the list
        } else {

            //...but there is footer, position of footer will be itemCount
            if (hasFooter && position == itemCount) {
                VIEW_TYPE_LOADING

                //...else it is movie item
            } else {
                VIEW_TYPE_LANGUAGE
            }
        }
    }

    //function for setting custom LoadStateAdapters, because default one does not show loadState on initial request
    fun withCustomLoadStateHeaderAndFooter(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>,
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->

            //if append or refresh state is Loading or Showing error text, there is footer in the list
            hasFooter = loadStates.append is LoadState.Loading ||
                    loadStates.append is LoadState.Error ||
                    loadStates.refresh is LoadState.Loading ||
                    loadStates.refresh is LoadState.Error

            //if prepend state is Loading or Showing error text, there is header in the list
            hasHeader = loadStates.prepend is LoadState.Loading ||
                    loadStates.prepend is LoadState.Error

            header.loadState = loadStates.prepend
            footer.loadState = when (loadStates.refresh) {
                is LoadState.NotLoading -> loadStates.append
                else -> loadStates.refresh
            }
        }
        return ConcatAdapter(header, this, footer)
    }

    inner class LanguageVH(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onLanguageClick(
                    getItem(bindingAdapterPosition)?.id
                        ?: throw NullPointerException("There is no language")
                )
            }
        }

        fun onBindMovie(language: Language) = with(binding) {

            tvTitle.text = language.name
            // TODO: fix image bug in api & implement er model in android
//            imvImage.loadImageById(language.image.id)
        }
    }

    companion object {
        const val VIEW_TYPE_LANGUAGE = 1
        const val VIEW_TYPE_LOADING = 2

        val MOVIE_ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Language, newItem: Language) =
                oldItem == newItem
        }
    }
}