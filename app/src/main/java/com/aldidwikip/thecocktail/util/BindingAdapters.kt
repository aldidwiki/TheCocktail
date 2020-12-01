package com.aldidwikip.thecocktail.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.aldidwikip.thecocktail.R

@BindingAdapter("app:img_from_url")
fun imgFromUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_insert_photo_24)
        fallback(R.drawable.ic_baseline_insert_photo_24)
        error(R.drawable.ic_baseline_insert_photo_24)
    }
}