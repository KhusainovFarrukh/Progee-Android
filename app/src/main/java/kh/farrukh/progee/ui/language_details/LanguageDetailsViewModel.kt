package kh.farrukh.progee.ui.language_details

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.farrukh.progee.domain.framework_list.GetFrameworkListUseCase
import kh.farrukh.progee.domain.language_details.GetLanguageByIdUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 12:35 AM
 *kh.farrukh.progee.ui.language_details
 **/
@HiltViewModel
class LanguageDetailsViewModel @Inject constructor(
    private val getLanguageById: GetLanguageByIdUseCase,
    private val getFrameworkList: GetFrameworkListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val languageId by lazy {
        LanguageDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).languageId
    }

    private val _viewState = MutableLiveData(LanguageDetailsViewState())
    val viewState: LiveData<LanguageDetailsViewState> get() = _viewState

    val frameworks by lazy { getFrameworkList(languageId).asLiveData().cachedIn(viewModelScope) }

    init {
        getLanguageById()
    }

    fun getLanguageById() = viewModelScope.launch {
        getLanguageById(languageId)
            .map { it.toMovieViewState(_viewState.value ?: LanguageDetailsViewState()) }
            .collect(_viewState::setValue)
    }
}