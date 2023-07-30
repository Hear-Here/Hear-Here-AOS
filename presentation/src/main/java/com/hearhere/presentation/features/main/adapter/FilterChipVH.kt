package com.hearhere.presentation.features.main.adapter

import androidx.databinding.ViewDataBinding
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.databinding.ItemFilterChipBinding

class FilterChipVH(var binding: ViewDataBinding) : BaseViewHolder(binding){
    override fun bind(binder: BaseItemBinder) {
        super.bind(binder)
        binder as FilterChipItemBinder
        binding as ItemFilterChipBinding
    }
}
