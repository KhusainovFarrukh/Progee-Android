package kh.farrukh.progee.domain.language_list

import androidx.paging.PagingData
import kh.farrukh.progee.data.language.LanguageRepository
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.language.models.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/23/22 10:48 AM
 *kh.farrukh.progee.domain.language_list
 **/
class GetLanguageListUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {

    operator fun invoke(sortType: SortType): Flow<PagingData<Language>> =
        languageRepository.getPagedLanguages(sortType)
}