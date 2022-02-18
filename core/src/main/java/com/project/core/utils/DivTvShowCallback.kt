package com.project.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.core.domain.model.TvShow

class DivTvShowCallback(private val oldTvShow: List<TvShow>, private val newTvShow: List<TvShow>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTvShow.size

    override fun getNewListSize(): Int = newTvShow.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTvShow[oldItemPosition].id == newTvShow[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTvShow[oldItemPosition].id == newTvShow[newItemPosition].id
}