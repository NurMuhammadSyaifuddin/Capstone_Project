package com.project.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.core.domain.model.Credit

class DivCreditCallback(private val oldCredit: List<Credit>, private val newCredit: List<Credit>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldCredit.size

    override fun getNewListSize(): Int = newCredit.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCredit[oldItemPosition].castId == newCredit[newItemPosition].castId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCredit[oldItemPosition].castId == newCredit[newItemPosition].castId
}