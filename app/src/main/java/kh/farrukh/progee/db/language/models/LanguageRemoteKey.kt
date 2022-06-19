package kh.farrukh.progee.db.language.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by farrukh_kh on 6/19/22 7:45 PM
 *kh.farrukh.progee.db.language.models
 **/
@Entity(tableName = "language_remote_keys")
data class LanguageRemoteKey(
    @PrimaryKey
    val id: Int = 0,
    val languageId: Long,
    val nextPage: Int?,
    val prevPage: Int?
)