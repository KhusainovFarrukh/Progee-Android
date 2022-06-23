package kh.farrukh.progee.ui.language_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.movix.utils.error_handle.HandledError
import kh.farrukh.progee.R
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.databinding.FragmentLanguageDetailsBinding
import kh.farrukh.progee.utils.loadImageById
import kh.farrukh.progee.utils.snackLong

/**
 *Created by farrukh_kh on 6/24/22 12:29 AM
 *kh.farrukh.progee.ui.language_details
 **/
@AndroidEntryPoint
class LanguageDetailsFragment : Fragment(R.layout.fragment_language_details) {

    private val binding by viewBinding(FragmentLanguageDetailsBinding::bind)
    private val viewModel by viewModels<LanguageDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setObservers()
    }

    private fun setupUi() = with(binding) {
        mcvBack.setOnClickListener { findNavController().navigateUp() }
        layoutError.btnRetry.setOnClickListener { viewModel.getLanguageById() }
    }

    private fun setObservers() = with(viewModel) {
        viewState.observe(viewLifecycleOwner, ::setViewState)
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

    private fun setFullError(viewState: LanguageDetailsViewState) = with(binding) {
        layoutError.root.isVisible = viewState.isEmpty
        layoutError.txvError.text = viewState.error?.message ?: "Something went wrong"
    }

    private fun setIsLoading(isLoading: Boolean) = with(binding) {
        lottieLoading.isVisible = isLoading
    }

    private fun handleLanguageError(error: HandledError) {
        snackLong(error.message) { viewModel.getLanguageById() }
    }
}