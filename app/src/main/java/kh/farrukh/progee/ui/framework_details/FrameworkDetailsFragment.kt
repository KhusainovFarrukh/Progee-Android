package kh.farrukh.progee.ui.framework_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.progee.utils.error_handle.HandledError
import kh.farrukh.progee.R
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.databinding.FragmentFrameworkDetailsBinding
import kh.farrukh.progee.utils.fadeTo
import kh.farrukh.progee.utils.loadImageById
import kh.farrukh.progee.utils.snackLong

/**
 *Created by farrukh_kh on 6/24/22 12:19 PM
 *kh.farrukh.progee.ui.framework_details
 **/
@AndroidEntryPoint
class FrameworkDetailsFragment : Fragment(R.layout.fragment_framework_details) {

    private val binding by viewBinding(FragmentFrameworkDetailsBinding::bind)
    private val viewModel by viewModels<FrameworkDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setObservers()
    }

    private fun setupUi() = with(binding) {
        mcvBack.setOnClickListener { findNavController().navigateUp() }
        layoutError.btnRetry.setOnClickListener { viewModel.getFrameworkById() }
    }

    private fun setObservers() = with(viewModel) {
        viewState.observe(viewLifecycleOwner, ::setViewState)
    }

    private fun setViewState(viewState: FrameworkDetailsViewState) {
        setFirstLoading(viewState.isFirstLoading)
        setIsLoading(viewState.isLoading)
        setFullError(viewState)
        viewState.error?.let { handleLanguageError(it) }
        viewState.framework?.let { setFrameworkToViews(it) }
    }

    private fun setFrameworkToViews(framework: Framework) = with(binding) {
        imvImage.loadImageById(framework.image.id)
        txvTitle.text = framework.name
        txvDescription.text = framework.description
    }

    private fun setFirstLoading(isLoading: Boolean) = with(binding) {
        layoutLoading.root.isVisible = isLoading
    }

    private fun setFullError(viewState: FrameworkDetailsViewState) = with(binding.layoutError) {
        root.isVisible = viewState.isEmpty
        txvError.text = viewState.error?.message ?: "Something went wrong"
    }

    private fun setIsLoading(isLoading: Boolean) = with(binding) {
        lottieLoading.fadeTo( isLoading)
    }

    private fun handleLanguageError(error: HandledError) {
        snackLong(error.message) { viewModel.getFrameworkById() }
    }
}