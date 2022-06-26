package kh.farrukh.progee.data.language.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.data.image.models.Image
import kh.farrukh.progee.data.user.models.User

/**
 *Created by farrukh_kh on 6/18/22 2:29 PM
 *kh.farrukh.progee.data.language.models
 **/
// TODO: write relations instead of embedded (all entities)
@Entity(tableName = "languages")
data class Language(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val state: PublishState,
    @Embedded(prefix = "image_")
    val image: Image,
    @Embedded(prefix = "author_")
    val author: User,
    val createdAt: String
)