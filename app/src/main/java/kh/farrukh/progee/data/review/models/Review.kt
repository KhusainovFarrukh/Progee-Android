package kh.farrukh.progee.data.review.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kh.farrukh.progee.api.review.models.ReviewValue
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.user.models.User

/**
 *Created by farrukh_kh on 6/24/22 11:55 PM
 *kh.farrukh.progee.data.review.models
 **/
@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey
    val id: Long,
    val body: String,
    val value: ReviewValue,
    val score: Int,
    @Embedded(prefix = "language_")
    val language: Language,
    val upVotes: List<Long>,
    val downVotes: List<Long>,
    @Embedded(prefix = "author_")
    val author: User,
    val createdAt: String
)
