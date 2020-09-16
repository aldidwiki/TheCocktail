package com.aldidwikip.thecocktail.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.aldidwikip.thecocktail.R

@BindingAdapter("img_from_url")
fun imgFromUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_insert_photo_24)
        fallback(R.drawable.ic_baseline_insert_photo_24)
        error(R.drawable.ic_baseline_insert_photo_24)
    }
}