package com.scissorboy.scissorboytest.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.scissorboy.scissorboytest.R

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .error(Glide.with(view.context).load(R.drawable.drawable_light_gray))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}