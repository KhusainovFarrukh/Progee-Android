package kh.farrukh.progee.data.language

import androidx.paging.PagingData
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.language.models.SortType
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/19/22 8:18 PM
 *kh.farrukh.progee.data.language
 **/
interface LanguageRepository {

    fun getPagedLanguages(sortType: SortType): Flow<PagingData<Language>>

    fun getLanguageById(languageId: Long): Flow<Result<Language>>
}