package kh.farrukh.progee.db.framework

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.db.framework.models.FrameworkRemoteKey

/**
 *Created by farrukh_kh on 6/24/22 2:22 AM
 *kh.farrukh.progee.db.framework
 **/
@Dao
interface FrameworkRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKey(remoteKey: FrameworkRemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKeys(remoteKeys: List<FrameworkRemoteKey>)

    @Query("SELECT * FROM framework_remote_keys WHERE frameworkId = :frameworkId")
    suspend fun getRemoteKeyByFrameworkId(frameworkId: Long): FrameworkRemoteKey

    @Query("DELETE FROM framework_remote_keys")
    suspend fun deleteAll()
}