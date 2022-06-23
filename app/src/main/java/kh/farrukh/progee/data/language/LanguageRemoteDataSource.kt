package kh.farrukh.progee.data.language

import kh.farrukh.progee.api.language.LanguageApi
import kh.farrukh.progee.api.language.models.LanguageApiModel
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/18/22 2:29 PM
 *kh.farrukh.progee.data.language
 **/
class LanguageRemoteDataSource @Inject constructor(
    private val languageApi: LanguageApi
) {

    suspend fun getLanguageById(languageId: Long): LanguageApiModel =
        languageApi.getLanguageById(languageId)
}