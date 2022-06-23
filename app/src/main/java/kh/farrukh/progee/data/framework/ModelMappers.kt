package kh.farrukh.progee.data.framework

import kh.farrukh.progee.api.framework.models.FrameworkApiModel
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.data.image.toImage
import kh.farrukh.progee.data.language.toLanguage
import kh.farrukh.progee.data.user.toUser

/**
 *Created by farrukh_kh on 6/24/22 2:26 AM
 *kh.farrukh.progee.data.framework.models
 **/
fun FrameworkApiModel.toFramework() = Framework(
    id = id,
    name = name,
    description = description,
    language = language.toLanguage(),
    state = state,
    image = image.toImage(),
    author = author.toUser(),
    createdAt = createdAt
)