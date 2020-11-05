package com.sunasterisk.appchat.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunasterisk.appchat.base.ListBinder

@BindingAdapter("setImage")
fun ImageView.setImage(imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(context).load(imageUrl).into(this)
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("notifyListChange")
fun <T> RecyclerView.notifyListChange(newList: List<T>?) {
    (adapter as ListBinder<List<T>>).setData(newList)
}
