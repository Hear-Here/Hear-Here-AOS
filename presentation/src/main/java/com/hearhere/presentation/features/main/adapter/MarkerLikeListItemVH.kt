package com.hearhere.presentation.features.main.adapter

import androidx.databinding.ViewDataBinding
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.databinding.ItemMarkerLikeListBinding

class MarkerLikeListItemVH (private val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun bind(binder: BaseItemBinder) {
        super.bind(binder)
        binder as MarkerLikeItemBinder
        binding as ItemMarkerLikeListBinding
    }
}