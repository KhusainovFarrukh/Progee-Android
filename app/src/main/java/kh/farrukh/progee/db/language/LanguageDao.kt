package kh.farrukh.progee.db.language

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kh.farrukh.progee.data.language.models.Language

/**
 *Created by farrukh_kh on 6/18/22 2:35 PM
 *kh.farrukh.progee.db.language
 **/
@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages")
    fun languagePagingSource(): PagingSource<Int, Language>

    @Query("DELETE FROM languages")
    suspend fun deleteAll()

    @Insert
    suspend fun addLanguage(language: Language)

    @Insert
    suspend fun addLanguages(languages: List<Language>)
}