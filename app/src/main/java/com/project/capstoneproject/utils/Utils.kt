package com.project.capstoneproject.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun showEmptyFavorite(icon: ImageView, title: TextView, desc: TextView){
    icon.visible()
    title.visible()
    desc.visible()
}

fun hideEmptyFavorite(icon: ImageView, title: TextView, desc: TextView){
    icon.gone()
    title.gone()
    desc.gone()
}

fun View.showSnackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val scope = CoroutineScope(dispatcher)