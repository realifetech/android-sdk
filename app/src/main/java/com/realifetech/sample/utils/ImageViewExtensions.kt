package com.realifetech.sample.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(
    context: Context?,
    url: String?
) {
    if (!url.isNullOrEmpty()) {
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        context?.let {
            Glide.with(it).load(url).centerCrop().apply(options).into(this)
        }
    }
}