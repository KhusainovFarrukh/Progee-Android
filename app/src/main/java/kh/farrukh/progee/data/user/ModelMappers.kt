package kh.farrukh.progee.data.user

import kh.farrukh.progee.api.user.models.UserApiModel
import kh.farrukh.progee.data.image.toImage
import kh.farrukh.progee.data.user.models.User

/**
 *Created by farrukh_kh on 6/19/22 8:15 PM
 *kh.farrukh.progee.data.user.models
 **/
fun UserApiModel.toUser() = User(
    id = id,
    name = name,
    role = role,
//    image = image.toImage(),
    email = email,
    username = username,
    isEnabled = isEnabled,
    isLocked = isLocked
)