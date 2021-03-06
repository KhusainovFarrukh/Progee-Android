package kh.farrukh.progee.domain.review_list

import androidx.paging.PagingData
import kh.farrukh.progee.api.review.models.ReviewValue
import kh.farrukh.progee.data.review.ReviewRepository
import kh.farrukh.progee.data.review.models.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/25/22 12:22 AM
 *kh.farrukh.progee.domain.review_list
 **/
class GetReviewListUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    operator fun invoke(languageId: Long, value: ReviewValue?): Flow<PagingData<Review>> =
        reviewRepository.getPagedReviews(languageId, value)
}