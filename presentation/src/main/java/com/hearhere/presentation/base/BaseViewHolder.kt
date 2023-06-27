package com.hearhere.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hearhere.presentation.BR

open class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(binder: BaseItemBinder) {
        binding.setVariable(BR.binder, binder)
    }

    fun executePendingBindings() {
        binding.executePendingBindings()
    }
}
