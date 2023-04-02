package com.hearhere.presentation.util

import androidx.databinding.ViewDataBinding
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.test.TestViewHolder

fun getVHDataBindingById(binding: ViewDataBinding, resId : Int) : BaseViewHolder {
    return when(resId){
        R.layout.item_test -> TestViewHolder(binding)
        else -> BaseViewHolder(binding)
    }
}