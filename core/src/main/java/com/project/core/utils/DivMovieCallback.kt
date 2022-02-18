package com.project.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.core.domain.model.Movie

class DivMovieCallback(private val oldMovie: List<Movie>, private val newMovie: List<Movie>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldMovie.size

    override fun getNewListSize(): Int = newMovie.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovie[oldItemPosition].id == newMovie[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovie[oldItemPosition].id == newMovie[newItemPosition].id
}