package com.hearhere.presentation.test

import androidx.databinding.ObservableField
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder

class TestBinder(
    override var itemId: Long = 0,
    val itemClickListener: (Long) -> Unit,
) : BaseItemBinder {

    override var itemLayoutId: Int = R.layout.item_test

    var text: ObservableField<String> = ObservableField("")

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }

    fun onClickItem() {
        itemClickListener(itemId) // viewmodel 트리거
    }
}
