package kh.farrukh.progee.data.user.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kh.farrukh.progee.api.global.models.UserRole
import kh.farrukh.progee.data.image.models.Image

/**
 *Created by farrukh_kh on 6/18/22 2:31 PM
 *kh.farrukh.progee.data.user.models
 **/
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Long,
    val name: String,
    val role: UserRole,
//    @Embedded
//    val image: Image,
    val email: String,
    val username: String,
    val isEnabled: Boolean,
    val isLocked: Boolean
)