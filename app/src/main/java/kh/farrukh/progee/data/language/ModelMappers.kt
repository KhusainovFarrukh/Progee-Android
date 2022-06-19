package kh.farrukh.progee.data.language

import kh.farrukh.progee.api.language.models.LanguageApiModel
import kh.farrukh.progee.data.image.toImage
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.user.toUser

/**
 *Created by farrukh_kh on 6/19/22 8:13 PM
 *kh.farrukh.progee.data.language
 **/
fun LanguageApiModel.toLanguage() = Language(
    id = id,
    name = name,
    description = description,
    state = state,
    image = image.toImage(),
    author = author.toUser(),
    createdAt = createdAt
)