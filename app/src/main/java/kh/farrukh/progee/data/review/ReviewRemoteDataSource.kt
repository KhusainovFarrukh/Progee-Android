package kh.farrukh.progee.data.review

import kh.farrukh.progee.api.review.ReviewApi
import kh.farrukh.progee.api.review.models.ReviewApiModel
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 11:55 PM
 *kh.farrukh.progee.data.review
 **/
class ReviewRemoteDataSource @Inject constructor(
    private val reviewApi: ReviewApi
) {

    suspend fun getReviewById(languageId: Long, reviewId: Long): ReviewApiModel =
        reviewApi.getReviewById(languageId, reviewId)
}