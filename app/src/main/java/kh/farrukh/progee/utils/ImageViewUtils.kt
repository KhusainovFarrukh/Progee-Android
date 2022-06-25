package kh.farrukh.progee.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kh.farrukh.progee.R


/**
 *Created by farrukh_kh on 3/6/22 8:43 PM
 *kh.farrukh.movix.utils
 **/

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.placeholder_image,
    imageOptions: RequestOptions? = null
) {
    val glide = Glide.with(context).load(url)

    if (imageOptions == null) {

        val customOptions = RequestOptions()
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .dontAnimate()
            .fitCenter()

        glide
            .thumbnail(Glide.with(context).load(placeholder).centerCrop())
            .apply(customOptions)
            .into(this)
    } else {
        glide
            .apply(imageOptions)
            .dontAnimate()
            .fitCenter()
            .into(this)
    }
}

fun ImageView.loadImageById(
    imageId: Long,
    @DrawableRes placeholder: Int = R.drawable.placeholder_image,
) {

    val imageOptions = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .dontAnimate()
        .fitCenter()

    loadImage(
        "${BASE_URL_PROGEE_API}images/$imageId/download",
        placeholder,
        imageOptions
    )
}