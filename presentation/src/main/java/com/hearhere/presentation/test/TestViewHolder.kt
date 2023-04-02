package com.hearhere.presentation.test

import androidx.databinding.ViewDataBinding
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.databinding.ItemTestBinding


class TestViewHolder(
    private val binding: ViewDataBinding,
    ) : BaseViewHolder(binding) {
    override fun bind(binder: BaseItemBinder) {
        super.bind(binder)
        binder as TestBinder
        binding as ItemTestBinding

        binding.root.setOnClickListener {
            binder.onClickItem()
        }
    }

}