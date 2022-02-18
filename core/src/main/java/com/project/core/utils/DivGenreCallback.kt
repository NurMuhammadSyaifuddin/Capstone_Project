package com.project.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.core.domain.model.Genre

class DivGenreCallback(private val oldGenre: List<Genre>, private val newGenre: List<Genre>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldGenre.size

    override fun getNewListSize(): Int = newGenre.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldGenre[oldItemPosition].id == newGenre[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldGenre[oldItemPosition].id == newGenre[newItemPosition].id
}