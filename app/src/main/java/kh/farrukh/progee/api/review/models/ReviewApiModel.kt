package kh.farrukh.progee.api.review.models

import com.google.gson.annotations.SerializedName
import kh.farrukh.progee.api.language.models.LanguageApiModel
import kh.farrukh.progee.api.user.models.UserApiModel

/**
 *Created by farrukh_kh on 6/24/22 11:36 PM
 *kh.farrukh.progee.api.review.models
 **/
data class ReviewApiModel(
    val id: Long,
    val body: String,
    val value: ReviewValue,
    val score: Int,
    val language: LanguageApiModel,
    @SerializedName("up_votes")
    val upVotes: List<Long>,
    @SerializedName("down_votes")
    val downVotes: List<Long>,
    val author: UserApiModel,
    @SerializedName("created_at")
    val createdAt: String
)
