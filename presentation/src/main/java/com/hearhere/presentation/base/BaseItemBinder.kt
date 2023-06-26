package com.hearhere.presentation.base

interface BaseItemBinder {
    var itemId: Long
    var itemLayoutId: Int

    fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean
}
