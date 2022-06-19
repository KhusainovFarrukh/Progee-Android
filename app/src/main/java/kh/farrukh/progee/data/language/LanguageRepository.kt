package kh.farrukh.progee.data.language

import androidx.paging.PagingData
import kh.farrukh.progee.data.language.models.Language
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/19/22 8:18 PM
 *kh.farrukh.progee.data.language
 **/
interface LanguageRepository {

    fun getPagedLanguages(): Flow<PagingData<Language>>
}