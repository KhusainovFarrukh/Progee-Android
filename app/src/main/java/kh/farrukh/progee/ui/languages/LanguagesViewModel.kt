package kh.farrukh.progee.ui.languages

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.farrukh.progee.data.language.models.SortType
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

    private val sortType = MutableLiveData<SortType>(SortType.Default)
    val languages = sortType.switchMap { sortType ->
        getLanguageList(sortType).asLiveData().cachedIn(viewModelScope)
    }

    fun setSortType(sortType: SortType) {
        this.sortType.value = sortType
    }
}