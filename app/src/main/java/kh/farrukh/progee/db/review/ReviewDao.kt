package kh.farrukh.progee.db.review

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kh.farrukh.progee.api.review.models.ReviewValue
import kh.farrukh.progee.data.review.models.Review
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/24/22 11:57 PM
 *kh.farrukh.progee.db.review
 **/
@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE language_id = :languageId AND value = :value")
    fun reviewPagingSourceByLanguageIdAndValue(languageId: Long, value: ReviewValue): PagingSource<Int, Review>

    @Query("SELECT * FROM reviews WHERE language_id = :languageId")
    fun reviewPagingSourceByLanguageId(languageId: Long): PagingSource<Int, Review>

    @Query("SELECT * FROM reviews WHERE id = :reviewId")
    fun getReviewById(reviewId: Long): Flow<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReview(review: Review)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReviews(review: List<Review>)

    @Query("DELETE FROM reviews")
    suspend fun deleteAll()
}