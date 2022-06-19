package kh.farrukh.progee.api.language.models

import com.google.gson.annotations.SerializedName
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.api.image.models.ImageApiModel
import kh.farrukh.progee.api.user.models.UserApiModel

/**
 *Created by farrukh_kh on 6/18/22 2:06 PM
 *kh.farrukh.progee.api.language.models
 **/
data class LanguageApiModel(
    val id: Long,
    val name: String,
    val description: String,
    val state: PublishState,
    val image: ImageApiModel,
    val author: UserApiModel,
    @SerializedName("created_at")
    val createdAt: String
)
