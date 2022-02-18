package com.project.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.core.databinding.ItemListDataBinding
import com.project.core.domain.model.Movie
import com.project.core.utils.*

class MovieAdapter(private val type: Type): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var listener: ((Int) -> Unit)? = null

    var listMovies = mutableListOf<Movie>()
    set(value) {
        val callback = DivMovieCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie){
            binding.apply {
                poster.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${movie.posterPath}")
                tvTitle.text = movie.title.toString()

                listener?.let {
                    itemView.setOnClickListener { it(movie.id as Int) }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = when(type){
        Type.MAIN -> if (listMovies.size >= 7) 7 else listMovies.size
        Type.ALL -> listMovies.size
    }

    fun onClick(listener: ((Int) -> Unit)?){
        this.listener = listener
    }
}