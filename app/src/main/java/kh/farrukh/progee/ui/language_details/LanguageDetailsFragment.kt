package kh.farrukh.progee.ui.language_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.movix.utils.error_handle.HandledError
import kh.farrukh.movix.utils.error_handle.onError
import kh.farrukh.progee.R
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.databinding.FragmentLanguageDetailsBinding
import kh.farrukh.progee.ui.global.ListLoadStateAdapter
import kh.farrukh.progee.utils.loadImageById
import kh.farrukh.progee.utils.snackLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *Created by farrukh_kh on 6/24/22 12:29 AM
 *kh.farrukh.progee.ui.language_details
 **/
@AndroidEntryPoint
class LanguageDetailsFragment : Fragment(R.layout.fragment_language_details) {

    private val args by navArgs<LanguageDetailsFragmentArgs>()
    private val binding by viewBinding(FragmentLanguageDetailsBinding::bind)
    private val viewModel by viewModels<LanguageDetailsViewModel>()
    private val frameworkAdapter by lazy { FrameworkAdapter(::onFrameworkClick) }
    private val reviewAdapter by lazy {
        ReviewAdapter(::onReviewClick, ::onLikeClick, ::onDislikeClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setObservers()
    }

    private fun setupUi() = with(binding) {
        mcvBack.setOnClickListener { findNavController().navigateUp() }
        layoutError.btnRetry.setOnClickListener { viewModel.getLanguageById() }
        rvFrameworks.adapter = frameworkAdapter.withCustomLoadStateHeaderAndFooter(
            ListLoadStateAdapter(isVertical = false) { frameworkAdapter.retry() },
            ListLoadStateAdapter(isVertical = false) { frameworkAdapter.retry() }
        )
        rvReviews.adapter = reviewAdapter.withCustomLoadStateHeaderAndFooter(
            ListLoadStateAdapter { reviewAdapter.retry() },
            ListLoadStateAdapter { reviewAdapter.retry() }
        )
    }

    private fun setObservers() = with(viewModel) {
        viewState.observe(viewLifecycleOwner, ::setViewState)
        frameworks.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                frameworkAdapter.submitData(it)
            }
        }
        reviews.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                reviewAdapter.submitData(it)
            }
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
            frameworkAdapter.loadStateFlow.collect { loadStates ->
                if (loadStates.source.refresh is LoadState.NotLoading &&
                    (loadStates.mediator?.refresh is LoadState.Loading ||
                            loadStates.mediator?.refresh is LoadState.Error)
                ) {
                    binding.rvFrameworks.scrollToPosition(0)
                }

                loadStates.onError(::handleFrameworksError)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // TODO: this is temp solution. And has another bug: when using only db,
            //  if scroll many times, rv.scrollToPosition(0) is being triggered
            reviewAdapter.loadStateFlow.collect { loadStates ->
                if (loadStates.source.refresh is LoadState.NotLoading &&
                    (loadStates.mediator?.refresh is LoadState.Loading ||
                            loadStates.mediator?.refresh is LoadState.Error)
                ) {
                    binding.rvReviews.scrollToPosition(0)
                }

                loadStates.onError(::handleReviewsError)
            }
        }
    }

    private fun setViewState(viewState: LanguageDetailsViewState) {
        setFirstLoading(viewState.isFirstLoading)
        setIsLoading(viewState.isLoading)
        setFullError(viewState)
        viewState.error?.let { handleLanguageError(it) }
        viewState.language?.let { setLanguageToViews(it) }
    }

    private fun setLanguageToViews(language: Language) = with(binding) {
        imvImage.loadImageById(language.image.id)
        txvTitle.text = language.name
        txvDescription.text = language.description
    }

    private fun setFirstLoading(isLoading: Boolean) = with(binding) {
        layoutLoading.root.isVisible = isLoading
    }

    private fun setFullError(viewState: LanguageDetailsViewState) = with(binding.layoutError) {
        root.isVisible = viewState.isEmpty
        txvError.text = viewState.error?.message ?: "Something went wrong"
    }

    private fun setIsLoading(isLoading: Boolean) = with(binding) {
        lottieLoading.isVisible = isLoading
    }

    private fun handleLanguageError(error: HandledError) {
        snackLong(error.message) { viewModel.getLanguageById() }
    }

    private fun handleFrameworksError(error: HandledError) {
        snackLong(error.message) { frameworkAdapter.retry() }
    }

    private fun handleReviewsError(error: HandledError) {
        snackLong(error.message) { reviewAdapter.retry() }
    }

    private fun onFrameworkClick(frameworkId: Long) {
        findNavController().navigate(
            LanguageDetailsFragmentDirections.actionLanguageDetailsFragmentToFrameworkDetailsFragment(
                args.languageId, frameworkId
            )
        )
    }

    private fun onReviewClick(reviewId: Long) {
        // TODO: implement
    }

    private fun onLikeClick(reviewId: Long) {
        // TODO: implement
    }

    private fun onDislikeClick(reviewId: Long) {
        // TODO: implement
    }
}