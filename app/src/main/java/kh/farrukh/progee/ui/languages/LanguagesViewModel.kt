package kh.farrukh.progee.ui.languages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.farrukh.progee.domain.language_list.GetLanguageListUseCase
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/23/22 10:47 AM
 *kh.farrukh.progee.ui.languages
 **/
@HiltViewModel
class LanguagesViewModel @Inject constructor(
    private val getLanguageList: GetLanguageListUseCase
) : ViewModel() {

    val languages by lazy { getLanguageList().asLiveData().cachedIn(viewModelScope) }
}