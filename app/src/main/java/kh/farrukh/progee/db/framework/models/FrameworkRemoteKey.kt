package kh.farrukh.progee.db.framework.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by farrukh_kh on 6/24/22 2:22 AM
 *kh.farrukh.progee.db.framework.models
 **/
@Entity(tableName = "framework_remote_keys")
data class FrameworkRemoteKey(
    @PrimaryKey
    val id: Int = 0,
    val frameworkId: Long,
    val nextPage: Int?,
    val prevPage: Int?
)
