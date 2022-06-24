package kh.farrukh.progee.api.review.models

/**
 *Created by farrukh_kh on 6/24/22 11:42 PM
 *kh.farrukh.progee.api.review.models
 **/
enum class ReviewValue(val score: Int) {
    DISLIKE(-1),
    DONT_HAVE_PRACTICE(0),
    WANT_TO_LEARN(1),
    LIKE(2)
}