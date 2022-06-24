package kh.farrukh.progee.db.review

import androidx.room.TypeConverter

/**
 *Created by farrukh_kh on 6/25/22 2:19 AM
 *kh.farrukh.progee.db.review
 **/
class ReviewTypeConverters {

    @TypeConverter
    fun fromListLong(items: List<Long>): String = items.joinToString(",")

    @TypeConverter
    fun toListLong(string: String): List<Long> = string.split(",").mapNotNull { it.toLongOrNull() }
}