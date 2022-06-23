package kh.farrukh.progee.db.language

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.data.language.models.Language
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/18/22 2:35 PM
 *kh.farrukh.progee.db.language
 **/
@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages")
    fun languagePagingSource(): PagingSource<Int, Language>

    @Query("SELECT * FROM languages WHERE id = :languageId")
    fun getLanguageById(languageId: Long): Flow<Language>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLanguage(language: Language)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLanguages(languages: List<Language>)

    @Query("DELETE FROM languages")
    suspend fun deleteAll()
}