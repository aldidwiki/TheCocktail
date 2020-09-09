package com.aldidwikip.thecocktail.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("img_from_url")
fun imgFromUrl(imageView: ImageView, url: String?) {
    imageView.load(url)
}