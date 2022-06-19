package kh.farrukh.progee.api.user.models

import com.google.gson.annotations.SerializedName
import kh.farrukh.progee.api.global.models.UserRole
import kh.farrukh.progee.api.image.models.ImageApiModel

/**
 *Created by farrukh_kh on 6/18/22 2:13 PM
 *kh.farrukh.progee.api.user.models
 **/
data class UserApiModel(
    val id: Long,
    val name: String,
    val role: UserRole,
    val image: ImageApiModel,
    val email: String,
    val username: String,
    @SerializedName("is_enabled")
    val isEnabled: Boolean,
    @SerializedName("is_locked")
    val isLocked: Boolean
)
