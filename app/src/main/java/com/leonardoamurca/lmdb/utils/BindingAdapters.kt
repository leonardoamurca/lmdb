package com.leonardoamurca.lmdb.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leonardoamurca.lmdb.BuildConfig
import com.leonardoamurca.lmdb.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, path: String?) {
    val imgUrl = "${BuildConfig.ASSETS_BASE_URL}$path"
    imgUrl.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}