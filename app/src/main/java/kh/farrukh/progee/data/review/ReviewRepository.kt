package kh.farrukh.progee.data.review

import androidx.paging.PagingData
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/25/22 12:17 AM
 *kh.farrukh.progee.data.review
 **/
interface ReviewRepository {

    fun getPagedReviews(languageId: Long): Flow<PagingData<Review>>

    fun getReviewById(languageId: Long, reviewId: Long): Flow<Result<Review>>
}