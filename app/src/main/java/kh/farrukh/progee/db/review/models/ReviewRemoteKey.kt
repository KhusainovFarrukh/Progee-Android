package kh.farrukh.progee.db.review.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by farrukh_kh on 6/24/22 11:57 PM
 *kh.farrukh.progee.db.review.models
 **/
@Entity(tableName = "review_remote_keys")
data class ReviewRemoteKey(
    @PrimaryKey
    val id: Int = 0,
    val reviewId: Long,
    val nextPage: Int?,
    val prevPage: Int?
)