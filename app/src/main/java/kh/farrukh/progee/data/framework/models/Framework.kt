package kh.farrukh.progee.data.framework.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.data.image.models.Image
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.user.models.User

/**
 *Created by farrukh_kh on 6/24/22 2:25 AM
 *kh.farrukh.progee.data.framework.models
 **/
// TODO: write relations instead of embedded (all entities)
@Entity(tableName = "frameworks")
data class Framework(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    @Embedded(prefix = "language_")
    val language: Language,
    val state: PublishState,
    @Embedded(prefix = "image_")
    val image: Image,
    @Embedded(prefix = "author_")
    val author: User,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)