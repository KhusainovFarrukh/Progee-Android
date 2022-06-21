package kh.farrukh.progee.data.image.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by farrukh_kh on 6/18/22 2:30 PM
 *kh.farrukh.progee.data.image.models
 **/
@Entity(tableName = "images")
data class Image(
    @PrimaryKey
    val id: Long,
    val location: String
)
