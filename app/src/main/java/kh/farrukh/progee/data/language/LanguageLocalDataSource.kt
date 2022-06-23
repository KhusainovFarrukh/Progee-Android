package kh.farrukh.progee.data.language

import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.db.CacheDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/18/22 2:29 PM
 *kh.farrukh.progee.data.language
 **/
class LanguageLocalDataSource @Inject constructor(
    private val cacheDatabase: CacheDatabase
) {

    private val languageDao by lazy { cacheDatabase.languageDao() }

    fun getLanguageById(languageId: Long): Flow<Language> =
        languageDao.getLanguageById(languageId)

    suspend fun saveLanguage(language: Language) =
        languageDao.saveLanguage(language)
}