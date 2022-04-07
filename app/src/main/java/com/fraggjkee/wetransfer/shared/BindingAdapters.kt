package com.fraggjkee.wetransfer.shared

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl", "placeholder", requireAll = false)
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
    placeholder: Drawable?
) {
    Glide.with(imageView)
        .load(imageUrl)
        .placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}