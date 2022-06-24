package kh.farrukh.progee.db.framework

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.data.framework.models.Framework
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/24/22 2:22 AM
 *kh.farrukh.progee.db.framework
 **/
@Dao
interface FrameworkDao {

    @Query("SELECT * FROM frameworks WHERE language_id = :languageId")
    fun frameworkPagingSourceByLanguageId(languageId: Long): PagingSource<Int, Framework>

    @Query("SELECT * FROM frameworks WHERE id = :frameworkId")
    fun getFrameworkById(frameworkId: Long): Flow<Framework>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFramework(framework: Framework)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFrameworks(framework: List<Framework>)

    @Query("DELETE FROM frameworks")
    suspend fun deleteAll()
}