package com.hearhere.presentation.base

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hearhere.presentation.BR
import com.hearhere.presentation.R
import com.hearhere.presentation.test.TestViewHolder

open class BaseViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

    open fun bind(binder: BaseItemBinder){
        binding.setVariable(BR.binder,binder)
    }

    fun executePendingBindings() {
        binding.executePendingBindings()
    }
}

