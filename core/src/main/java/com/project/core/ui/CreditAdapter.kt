package com.project.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.core.databinding.ItemListTopCastBinding
import com.project.core.domain.model.Credit
import com.project.core.utils.BASE_URL_API_IMAGE
import com.project.core.utils.DivCreditCallback
import com.project.core.utils.POSTER_SIZE_W185
import com.project.core.utils.loadImage

class CreditAdapter: RecyclerView.Adapter<CreditAdapter.ViewHolder>() {

    var credits = mutableListOf<Credit>()
    set(value) {
        val callback = DivCreditCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListTopCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(credit: Credit){
            binding.apply {
                imgProfile.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${credit.profilePath}")
                tvName.text = credit.name.toString()
                tvCharacter.text = credit.character.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemListTopCastBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(credits[position])
    }

    override fun getItemCount(): Int = credits.size
}