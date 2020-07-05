package me.javicabanas.randomuser.androidcommons.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageUrl(url: String, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

fun ImageView.setCircularImageUrl(url: String, placeholder: Int? = null, error: Int? = null) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

private fun <T> RequestBuilder<T>.error(error: Int?): RequestBuilder<T> =
    error?.let { this.error(it) } ?: this

private fun <T> RequestBuilder<T>.placeholder(placeholderId: Int?): RequestBuilder<T> =
    placeholderId?.let { this.placeholder(it) } ?: this
