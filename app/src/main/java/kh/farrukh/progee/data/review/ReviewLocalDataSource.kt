package kh.farrukh.progee.data.review

import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.db.CacheDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 11:55 PM
 *kh.farrukh.progee.data.review
 **/
class ReviewLocalDataSource @Inject constructor(
    private val cacheDatabase: CacheDatabase
) {

    private val reviewDao by lazy { cacheDatabase.reviewDao() }

    fun getReviewById(reviewId: Long): Flow<Review> =
        reviewDao.getReviewById(reviewId)

    suspend fun saveReview(review: Review) =
        reviewDao.saveReview(review)
}