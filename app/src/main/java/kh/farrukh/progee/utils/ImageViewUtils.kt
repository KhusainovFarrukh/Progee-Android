package kh.farrukh.progee.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
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
    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    if (imageOptions == null) {
        glide
            .thumbnail(Glide.with(context).load(placeholder).centerCrop())
            .transition(withCrossFade(factory))
            .centerCrop()
            .error(R.drawable.placeholder_image)
            .into(this)
    } else {
        glide.apply(imageOptions)
            .transition(withCrossFade(factory))
            .into(this)
    }
}

fun ImageView.loadImageById(
    imageId: Long,
    @DrawableRes placeholder: Int = R.drawable.placeholder_image,
    imageOptions: RequestOptions? = null
) {
    loadImage(
        "${BASE_URL_PROGEE_API}images/$imageId/download",
        placeholder,
        imageOptions
    )
}