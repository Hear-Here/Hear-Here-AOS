package com.hearhere.presentation.util

import androidx.databinding.ViewDataBinding
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.features.main.adapter.MarkerLikeListItemVH
import com.hearhere.presentation.features.main.adapter.MarkerListItemVH
import com.hearhere.presentation.test.TestViewHolder

fun getVHDataBindingById(binding: ViewDataBinding, resId: Int): BaseViewHolder {
    return when (resId) {
        R.layout.item_test -> TestViewHolder(binding)
        R.layout.item_marker_list -> MarkerListItemVH(binding)
        R.layout.item_marker_like_list -> MarkerLikeListItemVH(binding)
        else -> BaseViewHolder(binding)
    }
}
