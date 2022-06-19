package kh.farrukh.progee.data.image

import kh.farrukh.progee.api.image.models.ImageApiModel
import kh.farrukh.progee.data.image.models.Image

/**
 *Created by farrukh_kh on 6/19/22 8:16 PM
 *kh.farrukh.progee.data.image.models
 **/
fun ImageApiModel.toImage() = Image(
    id = id,
    location = location
)