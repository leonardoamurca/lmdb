package com.leonardoamurca.lmdb.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardoamurca.lmdb.BuildConfig

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, path: String?) {

    // TODO: Render image fallback from local assets
    val imgUrl = path?.let { "${BuildConfig.ASSETS_BASE_URL}$it" }
        ?: "https://i.postimg.cc/287kwBgP/image-not-found.jpg"

    val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

    Glide.with(imgView.context)
        .load(imgUri)
        .into(imgView)

}

@BindingAdapter("present")
fun View.setPresent(status: Boolean) =
    if (status) this.visibility = View.VISIBLE else this.visibility = View.GONE

@BindingAdapter(value = ["adapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("visible")
fun View.setVisible(status: Boolean) =
    if (status) this.visibility = View.VISIBLE else this.visibility = View.INVISIBLE
