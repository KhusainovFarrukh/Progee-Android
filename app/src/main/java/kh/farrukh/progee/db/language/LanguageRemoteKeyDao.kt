package kh.farrukh.progee.db.language

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.db.language.models.LanguageRemoteKey

/**
 *Created by farrukh_kh on 6/19/22 7:46 PM
 *kh.farrukh.progee.db.language
 **/
@Dao
interface LanguageRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKey(remoteKey: LanguageRemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<LanguageRemoteKey>)

    @Query("SELECT * FROM language_remote_keys WHERE languageId = :languageId")
    suspend fun getRemoteKeyByLanguageId(languageId: Long): LanguageRemoteKey

    @Query("DELETE FROM language_remote_keys")
    suspend fun deleteAll()
}