package kh.farrukh.progee.api.review

import kh.farrukh.progee.api.global.models.PagingApiResponse
import kh.farrukh.progee.api.review.models.ReviewApiModel
import kh.farrukh.progee.api.review.models.ReviewValue
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by farrukh_kh on 6/24/22 11:35 PM
 *kh.farrukh.progee.api.review
 **/
interface ReviewApi {

    @GET("languages/{languageId}/reviews")
    suspend fun getReviews(
        @Path("languageId") languageId: Long,
        @Query("value") value: ReviewValue? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("sort_by") sortBy: String? = null,
        @Query("order_by") orderBy: String? = null
    ): PagingApiResponse<ReviewApiModel>

    @GET("languages/{languageId}/reviews/{reviewId}")
    suspend fun getReviewById(
        @Path("languageId") languageId: Long,
        @Path("reviewId") reviewId: Long
    ): ReviewApiModel
}