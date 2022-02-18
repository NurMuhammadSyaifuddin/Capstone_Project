package com.project.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

const val BASE_URL_API_IMAGE = "https://image.tmdb.org/t/p/"
const val POSTER_SIZE_W185 = "w185"
const val POSTER_SIZE_W780 = "w780"

fun ImageView.loadImage(url: String){
    Glide.with(this.context)
        .load(url)
        .placeholder(android.R.color.darker_gray)
        .into(this)
}