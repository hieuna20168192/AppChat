package com.sunasterisk.appchat.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImage")
fun ImageView.setImage(imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(context).load(imageUrl).into(this)
    }
}
