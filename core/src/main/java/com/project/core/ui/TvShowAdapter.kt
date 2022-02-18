package com.project.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.core.databinding.ItemListDataBinding
import com.project.core.domain.model.TvShow
import com.project.core.utils.*

class TvShowAdapter(private val type: Type): RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    private var listener: ((Int) -> Unit)? = null

    var tvshow = mutableListOf<TvShow>()
    set(value) {
        val callback = DivTvShowCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvShow){
            binding.apply {
                poster.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${data.posterPath}")
                tvTitle.text = data.name.toString()

                listener?.let {
                    itemView.setOnClickListener { it(data.id) }
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
        holder.bind(tvshow[position])
    }

    override fun getItemCount(): Int =
        when(type){
            Type.MAIN -> if (tvshow.size >= 7) 7 else tvshow.size
            Type.ALL -> tvshow.size
        }

    fun onClick(listener: ((Int) -> Unit)){
        this.listener = listener
    }
}