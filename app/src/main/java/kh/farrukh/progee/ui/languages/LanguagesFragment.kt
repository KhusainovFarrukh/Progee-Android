package kh.farrukh.progee.ui.languages

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.movix.utils.error_handle.HandledError
import kh.farrukh.movix.utils.error_handle.onError
import kh.farrukh.progee.R
import kh.farrukh.progee.data.language.models.SortType
import kh.farrukh.progee.data.language.models.languageSortTypes
import kh.farrukh.progee.databinding.FragmentLanguagesBinding
import kh.farrukh.progee.ui.global.ListLoadStateAdapter
import kh.farrukh.progee.ui.global.SortByAdapter
import kh.farrukh.progee.ui.languages.LanguageAdapter.Companion.VIEW_TYPE_LANGUAGE
import kh.farrukh.progee.ui.languages.LanguageAdapter.Companion.VIEW_TYPE_LOADING
import kh.farrukh.progee.utils.fadeTo
import kh.farrukh.progee.utils.snackShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *Created by farrukh_kh on 6/21/22 10:40 AM
 *kh.farrukh.progee.ui.languages
 **/
@AndroidEntryPoint
class LanguagesFragment : Fragment(R.layout.fragment_languages) {

    private val binding by viewBinding(FragmentLanguagesBinding::bind)
    private val viewModel by viewModels<LanguagesViewModel>()
    private val languageAdapter by lazy { LanguageAdapter(::onLanguageClick) }
    private val sortByAdapter by lazy { SortByAdapter(languageSortTypes(), ::onSortTypeClicked) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setObservers()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUi() = with(binding) {
        rvSortType.adapter = sortByAdapter
        mcvSortBy.setOnClickListener { mcvSortMenu.fadeTo(!binding.mcvSortMenu.isVisible) }
        touchOutside.setOnTouchListener { _, event ->
            if (mcvSortMenu.isVisible) {
                val sortByRect = Rect()
                mcvSortMenu.getGlobalVisibleRect(sortByRect)

                if (!sortByRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    mcvSortMenu.fadeTo(false)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (languageAdapter.getItemViewType(position)) {
                    VIEW_TYPE_LANGUAGE -> 1
                    VIEW_TYPE_LOADING -> 2
                    else -> throw IllegalArgumentException()
                }
            }
        }

        rvLanguages.setHasFixedSize(true)
        rvLanguages.layoutManager = gridLayoutManager
        rvLanguages.adapter = languageAdapter.withCustomLoadStateHeaderAndFooter(
            ListLoadStateAdapter { languageAdapter.retry() },
            ListLoadStateAdapter { languageAdapter.retry() }
        )
    }

    private fun setObservers() = with(viewModel) {
        languages.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { languageAdapter.submitData(it) }
        }

        // TODO: not saving rv scroll position on navigate back from details screen (when used only db)

        /** TODO: there are many bugs (and also many solutions) with scroll when using RemoteMediator.
        Currently custom temp solution is being used. See other solutions:
        1. from android arch components samples repo (LoadStatesMerger class)
        2. from google codelabs: paging3 repo (with UiState class, hasScrolledForThisSearch variable & etc.)
        3. other custom solutions (from GitHub, Medium and SO)

        Bugs:
        1. scroll to top when used only db (1st page) DONE
        2. scroll to top when used both api&db (1st page) DONE
        (now scrolling after 1st page response, not on start)
        3. new page is not shown when new page fetch via api TO_DO
        (now firstly showing ProgressBar, then scrolling top page to match bottom.
        But part of the new page items must be visible)
        4. scroll to top when navigate back from details frag DONE
         **/
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // TODO: this is temp solution. And has another bug: when using only db,
            //  if scroll many times, rv.scrollToPosition(0) is being triggered
            languageAdapter.loadStateFlow.collect { loadStates ->
                if (loadStates.source.refresh is LoadState.NotLoading &&
                    (loadStates.mediator?.refresh is LoadState.Loading ||
                            loadStates.mediator?.refresh is LoadState.Error)
                ) {
                    binding.rvLanguages.scrollToPosition(0)
                }

                loadStates.onError(::showError)
            }
        }
    }

    // TODO: handle when there are data along with error, and when there aren't
    // TODO: but don't forget about initial loading (when list is empty)
    private fun showError(error: HandledError) {
        snackShort(error.message) { languageAdapter.retry() }
    }

    private fun onSortTypeClicked(sortType: SortType) {
        binding.tvSortBy.text = "Sort by: ${sortType.label}"
        viewModel.setSortType(sortType)
        binding.mcvSortMenu.fadeTo(false)
    }

    private fun onLanguageClick(languageId: Long) {
        findNavController().navigate(
            LanguagesFragmentDirections.actionLanguagesFragmentToLanguageDetailsFragment(languageId)
        )
    }
}