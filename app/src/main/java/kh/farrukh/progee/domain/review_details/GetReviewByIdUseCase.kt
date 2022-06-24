package kh.farrukh.progee.domain.review_details

import kh.farrukh.progee.data.review.ReviewRepository
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/25/22 12:23 AM
 *kh.farrukh.progee.domain.review_details
 **/
class GetReviewByIdUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    operator fun invoke(languageId: Long, reviewId: Long): Flow<Result<Review>> =
        reviewRepository.getReviewById(languageId, reviewId)
}