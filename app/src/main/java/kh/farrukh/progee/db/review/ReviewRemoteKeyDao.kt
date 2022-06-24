package kh.farrukh.progee.db.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.db.review.models.ReviewRemoteKey

/**
 *Created by farrukh_kh on 6/24/22 11:57 PM
 *kh.farrukh.progee.db.review
 **/
@Dao
interface ReviewRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKey(remoteKey: ReviewRemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKeys(remoteKeys: List<ReviewRemoteKey>)

    @Query("SELECT * FROM review_remote_keys WHERE reviewId = :reviewId")
    suspend fun getRemoteKeyByReviewId(reviewId: Long): ReviewRemoteKey

    @Query("DELETE FROM review_remote_keys")
    suspend fun deleteAll()
}