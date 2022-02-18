package com.project.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.core.databinding.ItemListGenreBinding
import com.project.core.domain.model.Genre
import com.project.core.utils.DivGenreCallback

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    var genres = mutableListOf<Genre>()
    set(value) {
        val callback = DivGenreCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre){
            binding.apply {
                tvGenre.text = genre.name.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int = genres.size
}