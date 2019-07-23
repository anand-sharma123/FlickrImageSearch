package com.task.flickrimagesearch.utils

import android.graphics.Color
import android.support.v4.widget.CircularProgressDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun ImageView.loadImageFromLink(link: String?) {
    if (!link.isNullOrEmpty()) {
        Glide.with(context.applicationContext)
            .load(link)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(this)
    }
}

fun ImageView.loadImageFromLinkWithLoadingProgress(link: String?) {
    if (!link.isNullOrEmpty()) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 8f
        circularProgressDrawable.centerRadius = 48f
        circularProgressDrawable.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)
        circularProgressDrawable.start()

        Glide.with(context.applicationContext)
            .load(link)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(this)
    }
}