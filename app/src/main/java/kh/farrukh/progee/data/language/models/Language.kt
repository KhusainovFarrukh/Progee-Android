package kh.farrukh.progee.data.language.models

import androidx.room.Entity
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.data.image.models.Image
import kh.farrukh.progee.data.user.models.User

/**
 *Created by farrukh_kh on 6/18/22 2:29 PM
 *kh.farrukh.progee.data.language.models
 **/
@Entity(tableName = "languages")
data class Language(
    val id: Long,
    val name: String,
    val description: String,
    val state: PublishState,
    val image: Image,
    val author: User,
    val createdAt: String
)

// TODO: relations to Image and User