package kh.farrukh.progee.ui.framework_details

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.farrukh.progee.domain.framework_details.GetFrameworkByIdUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 12:19 PM
 *kh.farrukh.progee.ui.framework_details
 **/
@HiltViewModel
class FrameworkDetailsViewModel @Inject constructor(
    private val getFrameworkByIdUseCase: GetFrameworkByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args by lazy {
        FrameworkDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    private val _viewState = MutableLiveData(FrameworkDetailsViewState())
    val viewState: LiveData<FrameworkDetailsViewState> get() = _viewState

    init {
        getFrameworkById()
    }

    fun getFrameworkById() = viewModelScope.launch {
        getFrameworkByIdUseCase(args.languageId, args.frameworkId)
            .map { it.toFrameworkDetailsViewState(_viewState.value ?: FrameworkDetailsViewState()) }
            .collect(_viewState::setValue)
    }
}