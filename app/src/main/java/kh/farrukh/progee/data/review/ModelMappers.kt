package kh.farrukh.progee.data.review

import kh.farrukh.progee.api.review.models.ReviewApiModel
import kh.farrukh.progee.data.language.toLanguage
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.data.user.toUser

/**
 *Created by farrukh_kh on 6/25/22 12:12 AM
 *kh.farrukh.progee.data.review
 **/
fun ReviewApiModel.toReview() = Review(
    id = id,
    body = body,
    value = value,
    score = score,
    language = language.toLanguage(),
    upVotes = upVotes,
    downVotes = downVotes,
    author = author.toUser(),
    createdAt = createdAt
)