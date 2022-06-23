package kh.farrukh.progee.api.framework.models

import com.google.gson.annotations.SerializedName
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.api.image.models.ImageApiModel
import kh.farrukh.progee.api.language.models.LanguageApiModel
import kh.farrukh.progee.api.user.models.UserApiModel

/**
 *Created by farrukh_kh on 6/24/22 2:17 AM
 *kh.farrukh.progee.api.framework.models
 **/
data class FrameworkApiModel(
    val id: Long,
    val name: String,
    val description: String,
    val state: PublishState,
    val image: ImageApiModel,
    val author: UserApiModel,
    val language: LanguageApiModel,
    @SerializedName("created_at")
    val createdAt: String
)
